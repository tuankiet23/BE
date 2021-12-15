package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.JobRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface JobRegisterRepo extends JpaRepository<JobRegister,Long> {
    @Query(value = "from job_register u where u.isDelete = true ")
    List<JobRegister> getAllJR();
}
