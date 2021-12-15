package com.itsol.recruit_managerment.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobRegisterimpl implements JobRegisterService {
    @Autowired
    JobRegisterRepo jobRegisterRepo;
    @Autowired
    JobRepo jobRepo;
    @Autowired
    IUserRespository iUserRespository;
    @Autowired
    ProfileStatusRepo profileStatusRepo;
    @Autowired
    private EmailServiceImpl emailService;


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
            jobRegister.setDelete(false);
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
    public Object download(String fileName) throws IOException {
        String destFileName = UUID.randomUUID().toString().concat(fileName);     // to set random strinh for destination file name
        String destFilePath = "Z:\\New folder\\" + destFileName;                                    // to set destination file path

        ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("path of JSON with genarated private key"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of("your bucket name", fileName));
        blob.downloadTo(Paths.get(destFilePath));
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
