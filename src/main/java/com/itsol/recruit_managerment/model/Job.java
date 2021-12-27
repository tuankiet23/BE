package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBS_SEQ")
    @SequenceGenerator(name = "JOBS_SEQ", sequenceName = "JOBS_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "job_name")
    String jobName;

    @ManyToOne
    @JoinColumn(name = "job_position_id")
    JobPosition jobPosition;

    @Column(name = "number_experience ")
    Integer numberExperience;

    @ManyToOne
    @JoinColumn(name = "method_work_id")
    MethodWork method_work;

    @Column(name = "address_work")
    String addressWork;

    @ManyToOne
    @JoinColumn(name = "academic_level_id")
    AcademicLevel academicLevel ;

    @ManyToOne
    @JoinColumn(name = "level_id")
    LevelRank levelRank ;

    @Column(name = "qty_person ")
    Integer qtyPerson;

    @Column(name = "due_date")
    Date dueDate;

    @Column(name = "start_recruitment_date")
    Date startRecruitmentDate ;

    @Column(name = "skills")
    String skills;

    @Column(name = "description")
    String description;

    @Column(name = "interrest")
    String interrest;

    @Column(name = "salary_max")
    Integer salaryMax;

    @Column(name = "salary_min")
    Integer salaryMin;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    User contact;

    @ManyToOne
    @JoinColumn(name = "status_job_id")
    StatusJob statusJob;

    @Column(name = "views")
    Integer views;

    @ManyToOne
    @JoinColumn(name = "create_id")
    User creater;

    @Column(name = "createDate")
    Date createDate ;

    @Column(name = "isDelete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete ;
}
