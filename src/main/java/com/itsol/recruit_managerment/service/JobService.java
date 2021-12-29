package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.JobHomeDTO;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.vm.JobVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobVM;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobService {

    List<Job> getAllJob();

    Job getJobById(Long id);

    List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size);

    List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size);

    List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size);


    List<Job> getJobsWithCondition(int modJob, int startrow, int endrow);
    List<Job> searchJob(SearchJobVM searchJobVM, Integer pageIndex, Integer pageSize);

    JobHomeDTO jobHome();

}