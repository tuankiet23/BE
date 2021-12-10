package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.repositories.JobRegisterRepo;
import com.itsol.recruit_managerment.repositories.JobRepo;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRegisterimpl implements Task<JobRegister>, JobRegisterService{
    @Autowired
    JobRegisterRepo jobRegisterRepo;

    @Autowired
    JobRepo jobRepo;

    @Override
    public List<JobRegister> getAll() {
        return jobRegisterRepo.findAll();
    }

    @Override
    public JobRegister save(JobRegister jobRegister) {
        return null;
    }

    @Override
    public JobRegister getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
    @Override
    public Page<JobRegister> getJobRegister(Integer page, Integer size) {
            Pageable pageable = PageRequest.of(page, size);
            return jobRegisterRepo.findAll(pageable);
        }

    @Override
    public JobRegister saveJobRegister(JobRegister jobRegister) {
        return null;
    }

//    public int add(JobRegisterVM jobRegisterVM){
//        try{
//            JobRegister jobRegister=new JobRegister();
//
//
//        }catch (Exception e){
//
//        }
//    }

}
