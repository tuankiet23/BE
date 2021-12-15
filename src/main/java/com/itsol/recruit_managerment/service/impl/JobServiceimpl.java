package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.dto.AdminJobDTO;
import com.itsol.recruit_managerment.dto.JobDTO;
import com.itsol.recruit_managerment.model.*;

import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.service.JobService;



import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class JobServiceimpl implements JobService {

    JobRepo jobRepo;

    @Autowired
    private JobPositionRepo jobPositionRepo;

    @Autowired
    private AcademiclevelRepo academiclevelRepo;

    @Autowired
    private IUserRespository iUserRespository;

    @Autowired
    private MethodWorkRepo methodWorkRepo;

    @Autowired
    private StatusJobRepo statusJobRepo;


    @Override
    public List<Job> getAllJob() {
        return jobRepo.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        try {
            Job job= jobRepo.getJobById(id);
            Integer view= job.getViews() +1;
            jobRepo.updateView(id, view);
            return job;
        }catch (Exception e){
          log.error("failed get by id: " + id);
            return null;
        }
    }
    public AdminJobDTO getById(long id){
        AdminJobDTO adminJobDTO = new AdminJobDTO();
        try {
            JobPosition jobPosition = jobPositionRepo.getById(id);
            adminJobDTO.setJobPosition(jobPosition);
            AcademicLevel academicLevel = academiclevelRepo.getById(id);
            adminJobDTO.setAcademicLevel(academicLevel);
            MethodWork methodWork = methodWorkRepo.getById(id);
            adminJobDTO.setMethodWork(methodWork);
            StatusJob statusJob = statusJobRepo.getById(id);
            adminJobDTO.setStatusJob(statusJob);
            User userContact = iUserRespository.getById(id);
            adminJobDTO.setContact(userContact);
            User userCreate = iUserRespository.getById(id);
            adminJobDTO.setContact(userCreate);

            return  adminJobDTO;

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }
    public void add(JobDTO jobDTO){
        try {
            Job job = convert(jobDTO);
            jobRepo.save(job);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    public Job convert(JobDTO jobDTO ){
        AcademicLevel academicLevel = new AcademicLevel();
        JobPosition jobPosition = new JobPosition();
        LevelRank levelRank = new LevelRank();
        MethodWork methodWork = new MethodWork();
        StatusJob statusJob = new StatusJob();
        User user = new User();
        Job job = new Job();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
//        try{
//            job.setDueDate(simpleDateFormat.parse(jobDTO.getDue_date().toString()));
//        }catch (Exception e){
//            log.error(e.getMessage(),e);
//        }
        return Job.builder().academicLevel(academicLevel).
                jobName(jobDTO.getJob_name())
                .addressWork(jobDTO.getAddress_work())
                .description(jobDTO.getDescription())
                .salary(jobDTO.getSalary())
                .description(jobDTO.getDescription())
                .interrest(jobDTO.getInterrest())
                .skills(jobDTO.getSkills())
                .method_work(methodWork)
                .levelRank(levelRank)
                .jobPosition(jobPosition)
                .statusJob(statusJob)
                .contact(user)
                .creater(user)
                .dueDate(jobDTO.getDue_date())
                .createDate(jobDTO.getCreate_date())
                .startRecruitmentDate(jobDTO.getStart_recruitment_date())
                .build();

    }


    @Override
    public List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jobRepo.getListNewJobs(numberDate,  pageable);
    }

    @Override
    public List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepo.getListJobsHightSalary(salary,  pageable);
    }

    @Override
    public List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepo.getListJobsDeadLine(numberDate, pageable);
    }


}
