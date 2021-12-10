package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.model.Job_Position;
import com.itsol.recruit_managerment.service.JobPositionimpl;
import com.itsol.recruit_managerment.service.JobServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/jobpposition")
public class JobPositionController {
    @Autowired
    JobPositionimpl jobPositionimpl;
    @GetMapping()
    public List<Job_Position> getJobPosition(){
        return jobPositionimpl.getAll();
    }

    @PostMapping()
    public ResponseEntity<Object> add(@Valid @RequestBody Job_Position job_position) {
        try {

            jobPositionimpl.save(job_position);

            return  ResponseEntity.ok().body(job_position);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to create academic_level");
        }
    }

    @GetMapping("/{id}")
    public Job_Position getJobPositionById( @PathVariable Long id){

        return jobPositionimpl.getById(id);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> deleteJobPosition( @PathVariable Long id){
        jobPositionimpl.deleteById(id);
        return ResponseEntity.badRequest().body("abc");
    }
}
