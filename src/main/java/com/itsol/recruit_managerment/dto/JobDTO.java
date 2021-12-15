package com.itsol.recruit_managerment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    Long id;
    String job_name;
    Integer job_position_id;
    Integer number_experience;
    Integer method_work_id;
    String address_work;
    Integer academic_level_id;
    Integer level_id;
    Integer qty_person;
    Date due_date;
    Date create_date;
    Date start_recruitment_date;
    String skills;
    String description;
    String interrest;
    Integer salary;
    Integer contact_id;
    Integer job_status_id;
    Integer views;
    Integer create_id;
    Integer is_delete;
}
