package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.service.JobServiceimpl;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/job")
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {
    @Autowired
    JobServiceimpl jobServiceimpl;


    @CrossOrigin
    @GetMapping("/{id}")
    public Job getJobDetail(@PathVariable("id") Long id) {
        return jobServiceimpl.getJobById(id);
    }


    /*
    get listjob theo tieu chi
    -	Việc làm mới tuyển (là các job mới được published từ 0 tới 7 ngày).
    -	Việc làm tuyển gấp (là các việc làm sắp hết thời hạn tuyển dụng nhưng chưa tuyển đủ người).
    -	Việc làm lương cao (là các job đang tuyển có mức lương từ 18 triệu trở lên)
     */
    @CrossOrigin
    @GetMapping("/new-job/{numberdate}/{page}/{size}")
    public List<Job> getAllJobWithNewJob(@PathVariable("numberdate") Integer nunberDate, @PathVariable("page") Integer page
            , @PathVariable("size") Integer size) {
        return jobServiceimpl.getListNewJobs(nunberDate, page, size);
    }

    @CrossOrigin
    @GetMapping("/hight-salary/{page}/{size}")
    public List<Job> getAllJobWithHigthSalary(@RequestParam() Integer salary, @PathVariable("page") Integer page
            , @PathVariable("size") Integer size) {
        return jobServiceimpl.getListNewJobs(salary, page, size);
    }

    /*
     Danh sach cong viec dang tuyen gop api vao
       get listjob theo tieu chi
       -	Việc làm mới tuyển (là các job mới được published từ 0 tới 7 ngày).
       -	Việc làm tuyển gấp (là các việc làm sắp hết thời hạn tuyển dụng nhưng chưa tuyển đủ người).
       -	Việc làm lương cao (là các job đang tuyển có mức lương từ 18 triệu trở lên)
    */
    @CrossOrigin
    @GetMapping("/home-page/{page}/{size}")
    public List<Job> getListJobWithConditions(
            @RequestParam("modjob") Integer modJob
            , @PathVariable("page") Integer page
            , @PathVariable("size") Integer size) {

        /**
         * truongbb - hàm này đang có vấn đề:
         *      - 3 hàm này có thể viết thành 1 hàm duy nhất
         *      - những con số điều kiện search như nunberDate, salary, deadLine ==> đưa vào constant hoặc config
         */
        // get job mowis tuyen
        if (modJob == 0) {
            Integer nunberDate = 7;
            return jobServiceimpl.getListNewJobs(nunberDate, page, size);
        } else if (modJob == 1) {
            // get job luong cao
            Integer salary = 18000000;
            return jobServiceimpl.getListJobsHightSalary(salary, page, size);
        } else if (modJob == 2) {
            // get job deadline = 3 ngay
            Integer deadLine = 3;
            return jobServiceimpl.getListJobDeadLine(deadLine, page, size);
        } else {
            return null;
        }

    }

    @CrossOrigin
    @GetMapping()
    public List<Job> getJob() {
        return jobServiceimpl.getAllJob();
    }
}
