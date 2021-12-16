package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> getAllJob();

    Optional<Job> getJobById(Long id);

    List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size);

    List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size);

    List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size);
}