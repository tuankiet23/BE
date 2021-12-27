package com.itsol.recruit_managerment.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "job_register")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRegister implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_JOR_ID")
    @SequenceGenerator(name = "GEN_JOR_ID", sequenceName = "JOBREG_SEQ", allocationSize = 1,initialValue = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_register")
    private Date dateRegister;

    @ManyToOne
    @JoinColumn(name = "profile_status_id")
    private ProfileStatus profileStatus;

    @Column(name = "date_interview")
    private Date dateInterview;

    @Column(name = "method_interview")
    private String methodInterview;

    @Column(name = "cv_file")
    private String cv;

    @Column(name = "reason")
    private String reason;

    @Column(name = "IS_DELETE")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isDelete;
}
