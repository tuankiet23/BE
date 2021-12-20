package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.constant.DateTimeConstant;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.BaseRepository;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.repositories.JobRegisterRepo;

//import com.itsol.recruit_managerment.repositories.JobRepo;
import com.itsol.recruit_managerment.repositories.ProfileStatusRepo;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.utils.SqlReader;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import lombok.AllArgsConstructor;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.service.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobRegisterimpl extends BaseRepository implements  JobRegisterService {

    @Autowired
    JobRegisterRepo jobRegisterRepo;
    @Autowired
    IUserRespository iUserRespository;
    @Autowired
    ProfileStatusRepo profileStatusRepo;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    JobRepoJPA jobRepo;
    @Override
    public List<JobRegister> getAllJobRegister() {

        return jobRegisterRepo.findAll();
    }

    @Override
    public Page<JobRegister> getAll(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return jobRegisterRepo.findAll(pageable);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        try{
            JobRegister jobRegister=jobRegisterRepo.getById(id);
            jobRegister.setIsDelete(true);
            jobRegisterRepo.save(jobRegister);
            return true;

        }catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Boolean save(JobRegisterVM jobRegisterVM) {
        try{
            JobRegister jobRegister=convert(jobRegisterVM);
            jobRegisterRepo.save(jobRegister);
            System.out.println(jobRegisterVM.getProfilestatus());
            if(jobRegisterVM.getProfilestatus().compareTo("1")==0)
                emailService.sendSimpleMessage(jobRegister.getUser().getUserName(),
                        "Thư mời phỏng vấn",
                        "abc");
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }


    @Override
    public AdminJobRegisterDTO getJobRegisterById(Long id) {
        try{
            JobRegister jobRegister=jobRegisterRepo.getById(id);
            List<ProfileStatus> profileStatuses=profileStatusRepo.findAll();
            AdminJobRegisterDTO adminJobRegisterDTO = new AdminJobRegisterDTO();
            adminJobRegisterDTO.setJobRegister(jobRegister);
            adminJobRegisterDTO.setProfileStatuses(profileStatuses);
            return adminJobRegisterDTO;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<JobRegister> getAllJR() {
        try{
            List<JobRegister> jobRegister=jobRegisterRepo.getAllJR();
            return  jobRegister;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public JobRegister getDetailJR(Long id) {
        return jobRegisterRepo.getById(id);
    }


    @Override
    public Object download(String fileName)  {
        return null;
    }

    @Override
    public List<JobRegister> searchJobRegister(SearchJobRegisterVM searchJobRegisterVM) {
        try {
            String query = SqlReader.getSqlQueryById(SqlReader.ADMIN_MODULE, "search");
            Map<String, Object> parameters = new HashMap<>();
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getFullName())) {
                query += "and users.full_name like :p_name";
                parameters.put("p_name", searchJobRegisterVM.getFullName());
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getJobName())) {
                query += "and jobs.job_name like :p_job_name";
                parameters.put("p_job_name", searchJobRegisterVM.getJobName());
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getPhoneNumber())) {
                query += "and users.phone_number like :p_phone_number";
                parameters.put("p_phone_number", searchJobRegisterVM.getPhoneNumber());
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getDateInterview())) {
                query += "and job_register.date_interview = TO_DATE(:p_date_interview, 'yyyy-MM-dd')";
                parameters.put("p_date_interview", searchJobRegisterVM.getDateInterview());
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getDateInterview())) {
                query += "and job_register.DATE_REGISTER = TO_DATE(:p_date_register, 'yyyy-MM-dd')";
                parameters.put("p_date_register", searchJobRegisterVM.getDateRegister());
            }

            return getNamedParameterJdbcTemplate().query(query, parameters, new JobRegisterMapper());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public JobRegister convert(JobRegisterVM jobRegisterVM){
        try{
            JobRegister jobRegister;
            jobRegister=jobRegisterRepo.getById(Long.parseLong(jobRegisterVM.getId()));
            Long idp=Long.parseLong(jobRegisterVM.getId());
            ProfileStatus profileStatus=profileStatusRepo.getById(idp);
    //            jobRegister.setProfileStatus(profileStatus);
            SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstant.YYYYMMDD_FOMART);
            jobRegister.setDateInterview(sdf.parse(jobRegisterVM.getDateinterview()));
            jobRegister.setMethodInterview(jobRegisterVM.getMethodinterview());
            return jobRegister;
        }catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }



    class JobRegisterMapper implements RowMapper<JobRegister> {
        public JobRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobRegister dto = new JobRegister();

            Job job = new Job();
            job.setId(rs.getLong("job_id"));
            dto.setJob(jobRepo.findById(job.getId()).get());

            User user = new User();
            user.setId(rs.getLong("user_id"));
            dto.setUser(iUserRespository.findById(user.getId()).get());

            dto.setMethodInterview(rs.getString("method_interview"));
            dto.setDateRegister(rs.getDate("date_register"));
            dto.setDateInterview(rs.getDate("date_interview"));

            ProfileStatus profileStatus = new ProfileStatus();
            profileStatus.setId(rs.getLong("profile_status_id"));
            dto.setProfileStatus( profileStatusRepo.findById(profileStatus.getId()).get());

            dto.setCv(rs.getString("cv_file"));
            dto.setId(rs.getLong("id"));
            return dto;
        }
    }


}
