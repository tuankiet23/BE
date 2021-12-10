package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.Job_Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface JobPositionRepo extends JpaRepository<Job_Position,Long> {


    @Query(value = "from JobPosition u where u.id = :id ")
    Job_Position getById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value=" update JobPosition u set u.is_delete = 0 where u.id=:id")
    void delete(@Param("id") Long id);

}