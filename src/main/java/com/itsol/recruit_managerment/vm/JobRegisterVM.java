package com.itsol.recruit_managerment.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRegisterVM {
    String id;
    String profilestatus;
    String dateregister;
    String dateinterview;
    String methodinterview;
    String cv;
    String reason;
}
