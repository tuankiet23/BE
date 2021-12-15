package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileStatusRepo extends JpaRepository<ProfileStatus, Long> {
}
