package com.itsol.recruit_managerment.dto;

import com.itsol.recruit_managerment.model.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

public class JobDTO {

    Long id;

    String jobName;

    JobPosition jobPosition;

    Integer numberExperience;

    MethodWork method_work;

    String addressWork;

    AcademicLevel academicLevel ;

    LevelRank levelRank ;

    Integer qtyPerson;

    Date dueDate;

    Date startRecruitmentDate ;

    String skills;

    String description;

    String interrest;

    Integer salary;

    User contact;

    StatusJob statusJob;

    Integer views;

    User creater;

    Date createDate ;
}
