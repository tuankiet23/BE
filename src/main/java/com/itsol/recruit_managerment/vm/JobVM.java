package com.itsol.recruit_managerment.vm;

import com.itsol.recruit_managerment.model.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
public class JobVM {

    String jobName;

//    JobPosition jobPosition;

//    Integer numberExperience;

//    MethodWork method_work;

//    String addressWork;

//    AcademicLevel academicLevel ;

//    LevelRank levelRank ;

    Integer qtyPerson;

//    Date dueDate;
//
//    Date startRecruitmentDate ;
//
//    String skills;
//
//    String description;
//
//    String interrest;
//
//    Integer salary;

//    User contact;

//    StatusJob statusJob;

//    Integer views;

//    User creater;

//    Date createDate ;
}
