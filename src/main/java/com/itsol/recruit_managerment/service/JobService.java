package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.JobHomeDTO;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.vm.SearchJobVM;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> getAllJob();

    Job getJobById(Long id);
    Optional<Job> getJobAd(Long id);

    List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size);

    List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size);

    List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size);


    List<Job> getJobsWithCondition(int modJob, int startrow, int endrow);

    List<Job> searchJob(SearchJobVM searchJobVM, Integer pageIndex, Integer pageSize);

    List<Job> searchJobHomePage(SearchJobVM searchJobVM, int pageNumber, int pageSize);

    JobHomeDTO jobHome();

}