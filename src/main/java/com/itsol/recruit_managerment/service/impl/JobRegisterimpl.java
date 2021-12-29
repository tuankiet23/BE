package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.constant.DateTimeConstant;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.dto.JobRegisterDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.BaseRepository;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.repositories.JobRegisterRepo;
import com.itsol.recruit_managerment.repositories.ProfileStatusRepo;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.utils.DataUtil;
import com.itsol.recruit_managerment.utils.SqlReader;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itsol.recruit_managerment.constant.ContenEmailConstant.CONTENT;

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
            System.out.println(jobRegister);
            jobRegisterRepo.save(jobRegister);
            if(jobRegisterVM.getProfilestatus().compareTo("3")==0)
                sendEmail(jobRegister);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }
    public void sendEmail(JobRegister jobRegister){
        String Content = CONTENT;
       Content= Content.replace("X", jobRegister.getUser().getFullName() );
        Content= Content.replace("PTPV", jobRegister.getMethodInterview() );
        Content= Content.replace("ABC", jobRegister.getJob().getJobName() );
        Content= Content.replace("DD", jobRegister.getDateInterview().toString() );
        emailService.sendSimpleMessage(jobRegister.getUser().getEmail(),
                "Thư mời phỏng vấn",
                Content);
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
    public List<JobRegisterDTO> searchJobRegister(SearchJobRegisterVM searchJobRegisterVM, Integer pageIndex, Integer pageSize) {
        try {

            String query = SqlReader.getSqlQueryById(SqlReader.ADMIN_MODULE, "search");
            Map<String, Object> parameters = new HashMap<>();
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getFullName())) {
                query += " and UPPER(users.full_name) like :p_name";
                parameters.put("p_name", "%"+searchJobRegisterVM.getFullName()+"%");
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getJobName())) {
                query += " and UPPER(jobs.job_name) like :p_job_name";
                parameters.put("p_job_name", "%"+searchJobRegisterVM.getJobName()+"%");
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getPhoneNumber())) {
                query += " and users.phone_number like :p_phone_number";
                parameters.put("p_phone_number", "%"+searchJobRegisterVM.getPhoneNumber()+"%");
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getDateInterview())) {
                query += " and to_char(job_register.date_interview, 'yyyy-mm-dd') = :p_date_interview";
                parameters.put("p_date_interview",searchJobRegisterVM.getDateInterview());
            }
            if (!ObjectUtils.isEmpty(searchJobRegisterVM.getDateRegister())) {
                query += " and to_char(job_register.DATE_REGISTER, 'yyyy-mm-dd') = :p_date_register";
                parameters.put("p_date_register", searchJobRegisterVM.getDateRegister());
            }
            Integer p_startrow;
            Integer p_endrow;
            if(pageIndex==0)
            {
                 p_startrow=pageSize*pageIndex;
                 p_endrow=p_startrow+pageSize;
            }
            else {
                p_startrow=pageSize*pageIndex+1;
                p_endrow=p_startrow+pageSize-1;
            }

                query += " ), count_all as( select count (*) total from tempselect ), paging as( select * from tempselect  where ROWNR between :p_startrow and :p_endrow) select p.*, c.total from paging p, count_all c ";
                parameters.put("p_startrow", p_startrow);
                parameters.put("p_endrow", p_endrow);

            return getNamedParameterJdbcTemplate().query(query, parameters, new JobRegisterMapper());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }


    @Override
    public Resource downloadCv(Long applicantId) throws IOException {
        JobRegister jobRegister = jobRegisterRepo.getById(applicantId);
        if (ObjectUtils.isEmpty(jobRegister)) {
            throw new NullPointerException("Could not found applicant");
        }
        String cvFilePath = jobRegister.getCv();
        Path file = Paths.get(cvFilePath);
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Could not read the file!");
        }
        return resource;
    }

    @Override
    public String getCvFileName(String cvFilePath) {
        if (!DataUtil.isNotNullAndEmptyString(cvFilePath)) {
            throw new NullPointerException("CV file path is null");
        }
        String[] cvFilePaths = cvFilePath.split("/");
        return cvFilePaths[cvFilePaths.length - 1];
    }

    public JobRegister convert(JobRegisterVM jobRegisterVM){
        try{
            JobRegister jobRegister;
            jobRegister=jobRegisterRepo.getById(Long.parseLong(jobRegisterVM.getId()));
            System.out.println(jobRegister);
            if(!ObjectUtils.isEmpty(jobRegisterVM.getProfilestatus())){
                Long idp=Long.parseLong(jobRegisterVM.getProfilestatus());
                ProfileStatus profileStatus=profileStatusRepo.getById(idp);
                jobRegister.setProfileStatus(profileStatus);
            }
            if(!ObjectUtils.isEmpty(jobRegisterVM.getDateinterview())){
                SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstant.YYYYMMDDHH_FOMART);
                Date dateinterview=  sdf.parse(jobRegisterVM.getDateinterview());
                jobRegister.setDateInterview(dateinterview);
            }
            if (!ObjectUtils.isEmpty(jobRegisterVM.getMethodinterview())){
                jobRegister.setMethodInterview(jobRegisterVM.getMethodinterview());
            }
            if(!ObjectUtils.isEmpty(jobRegisterVM.getReason())){
                jobRegister.setReason(jobRegisterVM.getReason());
            }
            return jobRegister;
        }catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }



    class JobRegisterMapper implements RowMapper<JobRegisterDTO> {
        public JobRegisterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobRegisterDTO dto = new JobRegisterDTO();
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
            dto.setReason(rs.getString("reason"));
            dto.setTotalItem(rs.getInt("total"));
            return dto;
        }
    }


}
