package com.itsol.recruit_managerment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobHomeDTO {
    List<JobDTO> jobNew;
    List<JobDTO> jobSalaryHight;
    List<JobDTO> jobOld;
}
