package com.itsol.recruit_managerment.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchJobRegisterVM {
    String fullName;
    String jobName;
    String phoneNumber;
    Date dateRegister;
    Date dateInterview;


}
