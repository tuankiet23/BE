package com.itsol.recruit_managerment.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobVM {
    @NotEmpty
    @NotBlank(message = "jobName không được để trống")
    @Size(max = 200,message = "tên không được vượt quá 150 kí tự")
    String job_name     ;
    @NotEmpty(message = " số năm kinh nghiệm không được để trống phải lớn hơn 0")
    Integer number_experience;
    @NotEmpty(message = " địa chỉ làm việc phải nhỏ hơn 300 ký tự")
    String address_work;
    @NotEmpty(message = " số lượng cần tuyển luôn phải lớn hơn hoặc bằng 1")
    Integer qty_person;
    @NotEmpty(message = " ngày cấp  không được để trống")
    Date dueDate;
    @NotEmpty(message = " ngày bắt đầu không được để trống")
    Date startRecruitmentDate;
    @NotEmpty(message = " skill  không được để trống")
    String skills;
    @NotEmpty(message = " mô tả  không được để trống và không vượt quá 2000 từ")
    String description;
    @NotEmpty(message = "   không được để trống")
    Integer is_delete;
    @NotEmpty(message = "   không được để trống")
    Integer salary;
    @NotEmpty(message = "   không được để trống")
    String interrest;
    @NotNull
    Integer job_position_id;
    @NotNull
    Integer method_work_id;
    @NotNull
    Integer academic_level_id;
    @NotNull
    Long contact_id;
    @NotNull
    Long create_id;
    @NotNull
    Integer job_status_id;


}
