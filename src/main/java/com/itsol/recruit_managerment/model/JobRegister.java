package com.itsol.recruit_managerment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.stream.Stream;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "job_register")
public class JobRegister implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBREG_SEQ")
    @SequenceGenerator(name = "JOBREG_SEQ", sequenceName = "JOBREG_SEQ", allocationSize = 1, initialValue = 1)
    @Column(nullable = false)
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

    @Column(name = "IS_DELETE")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public Date getDateInterview() {
        return dateInterview;
    }

    public void setDateInterview(Date dateInterview) {
        this.dateInterview = dateInterview;
    }

    public String getMethodInterview() {
        return methodInterview;
    }

    public void setMethodInterview(String methodInterview) {
        this.methodInterview = methodInterview;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

}
