package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.dto.JobDTO;
import com.itsol.recruit_managerment.model.Job;

import java.util.List;

public interface JobRepository<T> {

    List<Job> getListJobWithConditonInHomePage(T obj);
//    List<Job> serchJob(T obj, Integer pageSize, Integer pageIndex);
}
