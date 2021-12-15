package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.constant.DateTimeConstant;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.email.EmailServiceImpl;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.repositories.JobRegisterRepo;

import com.itsol.recruit_managerment.repositories.JobRepo;
import com.itsol.recruit_managerment.repositories.ProfileStatusRepo;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import lombok.AllArgsConstructor;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.service.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class JobRegisterimpl implements  JobRegisterService {

    @Autowired
    JobRegisterRepo jobRegisterRepo;
    @Autowired
    @Autowired
    IUserRespository iUserRespository;
    @Autowired
    ProfileStatusRepo profileStatusRepo;
    @Autowired
    private EmailServiceImpl emailService;
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
            jobRegister.setIsDelete(false);
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
    public Object download(String fileName)  {
        return null;
    }

    public JobRegister convert(JobRegisterVM jobRegisterVM){
        try{
            JobRegister jobRegister;
            jobRegister=jobRegisterRepo.getById(Long.parseLong(jobRegisterVM.getId()));
            jobRegister.setProfileStatus(profileStatusRepo.getById(Long.parseLong(jobRegisterVM.getId())));
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


}
