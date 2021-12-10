package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.springframework.data.relational.core.sql.In;

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
    private Job_Position job_position;

    @Column(name = "number_experience ")
    Integer number_experience;

    @OneToOne
    @JoinColumn(name = "method_work_id")
    private Method_work method_work;

    @Column(name = "address_work")
    String address_work;

   
// feature/apigetJobDeadline
   // @JoinColumn(name = "academic_level_id")
    //private Academic_Level academic_level;
    //    @Column(name="academic_level_id")
    //    Integer academic_level_id;
    //    @Column(name = "level_id")
    //    Integer level_id;
    @OneToOne
    private Academic_Level academic_level ;
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

    //    @Column(name = "contact_id")
    //    Integer contact_id;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private User contact;

    //    @Column(name="job_status_id")
    //    Integer job_status_id;
    @OneToOne
    @JoinColumn(name = "status_job_id")
    private Status_Job status_job;
    @Column(name = "views")
    Integer views;
    //    @Column(name = "create_id")
    //    Integer create_id;
    @OneToOne
    @JoinColumn(name = "create_id")
    private User creater;

    @Column(name = "create_date", unique = false)
    private Date createDate ;


//     @OneToOne
//     private User contact ;
// //    @Column(name="job_status_id")
// //    Integer job_status_id;
// @OneToOne
// private Status_Job status_job ;


//     @Column(name = "views")
//     Integer views;
// //    @Column(name = "create_id")
// //    Integer create_id;
// @OneToOne
// private User creater ;
// >>>>>>> develop

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_delete ;
}
