package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.Job_Position;
import com.itsol.recruit_managerment.repositories.JobPositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobPositionimpl implements Task<Job_Position>{
    @Autowired
    JobPositionRepo jobPositionRepo;
    @Override
    public List<Job_Position> getAll() {
        return jobPositionRepo.findAll();
    }

    @Override
    public Job_Position save(Job_Position job_position) {
        return jobPositionRepo.save(job_position);
    }

    @Override
    public Job_Position getById(Long id) {
        return jobPositionRepo.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        jobPositionRepo.delete(id);
    }
}
