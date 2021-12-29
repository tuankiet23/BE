package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.vm.JobVM;
import com.itsol.recruit_managerment.vm.SearchJobVM;

import java.util.List;

public interface UserJobRepository {

    List<Job> getListJobWithCondition(int modjob, int pageNumber, int pageSize);

    List<Job> searchJobHomePage(SearchJobVM searchJobVM, int pageNumber, int pageSize);

}
