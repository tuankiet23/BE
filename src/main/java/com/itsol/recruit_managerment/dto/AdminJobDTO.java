package com.itsol.recruit_managerment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsol.recruit_managerment.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminJobDTO {
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    AcademicLevel academicLevel;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    JobPosition jobPosition;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    MethodWork methodWork;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    LevelRank levelRank;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    StatusJob statusJob;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    User create;
    @JsonIgnoreProperties(value = {"applications" , "hibernateLazyInitializer"})
    User contact;


}
