package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.Level_Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LevelRankRepo extends JpaRepository<Level_Rank,Long> {


    @Query(value = "from Level_Rank u where u.id = :id ")
    Level_Rank getById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value=" update Level_Rank u set u.is_delete = 0 where u.id=:id")
    void delete(@Param("id") Long id);

}
