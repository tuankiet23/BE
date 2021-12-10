package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ProfilesRepo extends JpaRepository<Profiles, Long> {

    @Query(value = "from Profiles u where u.user.id = :id ")
    Profiles getById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value=" update Profiles u set u.is_delete = 0 where u.id=:id")
    void delete(@Param("id") Long id);
}
