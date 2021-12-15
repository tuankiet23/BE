package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.JobDTO;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.vm.JobVM;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobService {
    Job getJobById(Long id);

    List<Job> getAllJob();

    List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size);

    List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size);

    List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size);
}