package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import com.itsol.recruit_managerment.repositories.JobRegisterRepo;
import com.itsol.recruit_managerment.repositories.ProfileStatusRepo;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.service.UploadCVService;
import com.itsol.recruit_managerment.utils.CommonConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

@Service
@Slf4j
public class UploadCVServiceImpl implements UploadCVService {

    private final Path root = Paths.get(CommonConst.DIRECTORY_UPLOAD_CV);

    @Autowired
    private IUserRespository iUserRespository;

    @Autowired
    private JobRepoJPA jobRepoJPA;

    @Autowired
    private ProfileStatusRepo profileStatusRepo;

    @Autowired
    JobRegisterRepo jobRegisterRepo;

    @Override
    public void init() {
        try {

            File directory = new File(String.valueOf(root));
            if(!directory.exists()){
                Files.createDirectory(root);
            }else{
                log.info("file is exits in backend");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(String username, MultipartFile file, Long jobId) {
        try {
            User user = iUserRespository.findByUserName(username);
            if(user ==null){
                log.error("username: " + username + "is not exits");
                    return;
            }
            String fileName = file.getOriginalFilename();

            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            file.transferTo(new File("F:\\upload\\"+fileName));
            // Save to job register
            registerJobForUser(user, fileName, jobId);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void registerJobForUser(User user, String fileName, Long jobId){

        String url = CommonConst.URL_DOWNLOAD_CV + fileName;
        Job job = jobRepoJPA.findById(jobId).get();
       JobRegister jobRegister ;

        jobRegister = JobRegister.builder()
                .job(job)
                .user(user)
                .cv(url)
                .dateRegister(new Date(System.currentTimeMillis()))
                .isDelete(false)
                .profileStatus(profileStatusRepo.findByName(CommonConst.WAITING))
                .build();
        System.out.println(jobRegister.getCv());
        jobRegisterRepo.save(jobRegister);
    }
    public Date convertMilisecondToDateType(Long milliSeconds){
        Date date = new Date(milliSeconds);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return date;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
