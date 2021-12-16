package com.itsol.recruit_managerment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminJobRegisterDTO {
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    JobRegister jobRegister;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    List<ProfileStatus> profileStatuses;
}
