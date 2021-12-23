package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.vm.JobVM;

import java.util.List;

public interface UserJobRepository {

    List<Job> getListJobWithCondition(int modjob, int pOffset, int pageSize);

}
