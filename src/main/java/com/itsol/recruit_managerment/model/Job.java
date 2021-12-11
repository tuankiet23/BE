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
    private Long id;

    @Column(name = "job_name")
    private String job_name;


    @OneToOne
    @JoinColumn(name = "job_position_id")
    private JobPosition job_position;

    @Column(name = "number_experience ")
    Integer number_experience;

    @OneToOne
    @JoinColumn(name = "method_work_id")
    private MethodWork method_work;

    @Column(name = "address_work")
    String address_work;

    @OneToOne
    private AcademicLevel academic_level ;

    @OneToOne
    private Level_Rank level_rank ;

    @Column(name = "qty_person ")
    Integer qty_person;

    @Column(name = "due_date")
    Date due_date;

    @Column(name = "skills")
    String skills;

    @Column(name = "description")
    String description;

    @Column(name = "interrest")
    String interrest;

    @Column(name = "salary")
    Integer salary;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private User contact;

    @OneToOne
    @JoinColumn(name = "status_job_id")
    private Status_Job status_job;

    @Column(name = "views")
    Integer views;

    @OneToOne
    @JoinColumn(name = "create_id")
    private User creater;

    @Column(name = "create_date", unique = false)
    private Date createDate ;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_delete ;
}
