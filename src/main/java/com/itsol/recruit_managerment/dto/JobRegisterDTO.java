package com.itsol.recruit_managerment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.ProfileStatus;
import com.itsol.recruit_managerment.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRegisterDTO {
    private Long id;
    private Job job;
    private User user;
    private Date dateRegister;
    private ProfileStatus profileStatus;
    private Date dateInterview;
    private String methodInterview;
    private String cv;
    private String reason;
    private Boolean isDelete;
    private Integer totalItem;
}
