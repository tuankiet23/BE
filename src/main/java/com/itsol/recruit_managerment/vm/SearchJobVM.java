package com.itsol.recruit_managerment.vm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchJobVM {
    String jobName;
    Integer salaryMin;
    Integer salaryMax;
}
