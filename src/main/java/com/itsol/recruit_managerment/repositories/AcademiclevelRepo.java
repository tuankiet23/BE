package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Academic_Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AcademiclevelRepo extends JpaRepository<Academic_Level, Long> {

    @Query(value = "from Academic_Level u where u.id = :id ")
    Academic_Level getById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = " update Academic_Level u set u.is_delete = 0 where u.id=:id")
    void deleteAc(@Param("id") Long id);

}
