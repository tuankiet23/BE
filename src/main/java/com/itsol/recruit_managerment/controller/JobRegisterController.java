package com.itsol.recruit_managerment.controller;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.service.impl.JobRegisterimpl;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user/jobregister")
public class JobRegisterController {
    @Autowired
    JobRegisterService jobRegisterService;

    @CrossOrigin
    @GetMapping("/{page}/{size}")
    public Page<JobRegister> getAllJobRegister(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return jobRegisterService.getAll(page, size);
    }


    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        Boolean flag=jobRegisterService.delete(id);
        if (flag==true){
            return ResponseEntity.badRequest().body("Xóa thành công");
        }
        return ResponseEntity.badRequest().body("Xóa thất bại");
    }

//    @CrossOrigin
//    @GetMapping()
//    public List<JobRegister> getAll(){
//        return jobRegisterService.getAllJobRegister();
//    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<String> addJobRegister(@Valid @RequestBody JobRegisterVM jobRegisterVM){
        Boolean flag= jobRegisterService.save(jobRegisterVM);
        if (flag==true){
            return ResponseEntity.badRequest().body("Thêm thành công");
        }
        return ResponseEntity.badRequest().body("Thêm thất bại");
    }
    @CrossOrigin
    @PutMapping()
    public ResponseEntity<String> updateJobRegister(@Valid @RequestBody JobRegisterVM jobRegisterVM){
        Boolean flag= jobRegisterService.save(jobRegisterVM);
        if (flag==true){
            return ResponseEntity.ok().body("Update thành công");
        }
        return ResponseEntity.ok().body("Update thất bại");
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public AdminJobRegisterDTO getDetailJobRegister(@PathVariable("id") Long id){
        return jobRegisterService.getJobRegisterById(id);
    }

    @PostMapping("/pic/{fileName}")
    public Object download(@PathVariable("fileName") String fileName) {
        return jobRegisterService.download(fileName);
    }

    @CrossOrigin()
    @GetMapping()
    public List<JobRegister> getAll(){
        return jobRegisterService.getAllJR();
    }

}