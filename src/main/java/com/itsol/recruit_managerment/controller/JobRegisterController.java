package com.itsol.recruit_managerment.controller;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.dto.JobRegisterDTO;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_TYPE;

@Slf4j
@RestController
@RequestMapping("/api/user/jobregister")
public class JobRegisterController {
    @Autowired
    JobRegisterService jobRegisterService;



    public static final String DIRECTORY = System.getProperty("user.home") + "/Desktop/uploads/";

    public static final String CONTENT_DISPOSITION = System.getProperty(".pdf");


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
            return ResponseEntity.badRequest().body("X??a th??nh c??ng");
        }
        return ResponseEntity.badRequest().body("X??a th???t b???i");
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
            return ResponseEntity.badRequest().body("Th??m th??nh c??ng");
        }
        return ResponseEntity.badRequest().body("Th??m th???t b???i");
    }
    @CrossOrigin
    @PutMapping()
    public ResponseEntity<String> updateJobRegister(@Valid @RequestBody JobRegisterVM jobRegisterVM){
        Boolean flag= jobRegisterService.save(jobRegisterVM);
        if (flag==true){
            return ResponseEntity.ok().body("Update th??nh c??ng");
        }
        return ResponseEntity.ok().body("Update th???t b???i");
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public AdminJobRegisterDTO getDetailJobRegister(@PathVariable("id") Long id){
        return jobRegisterService.getJobRegisterById(id);
    }


    @CrossOrigin()
    @GetMapping()
    public List<JobRegister> getAll(){
        return jobRegisterService.getAllJR();
    }

    @CrossOrigin()
    @PutMapping("/search")
    public List<JobRegisterDTO> search(@Valid @RequestBody SearchJobRegisterVM searchJobRegisterVM, @RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return jobRegisterService.searchJobRegister(searchJobRegisterVM, pageIndex, pageSize);
    }

    @GetMapping("/download/{id}")
    @CrossOrigin
    public ResponseEntity<Resource> downloadApplicantCv(@PathVariable("id") Long id) throws Exception {
        Resource resource = jobRegisterService.downloadCv(id);
        Path path = resource.getFile()
                .toPath();
        String contentType="application/pdf";
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}