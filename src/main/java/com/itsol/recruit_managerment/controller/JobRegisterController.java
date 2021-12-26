package com.itsol.recruit_managerment.controller;
import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.service.JobRegisterService;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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


    @CrossOrigin()
    @GetMapping()
    public List<JobRegister> getAll(){
        return jobRegisterService.getAllJR();
    }

    @CrossOrigin()
    @PutMapping("/search")
    public List<JobRegister> search(@Valid @RequestBody SearchJobRegisterVM searchJobRegisterVM, @RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return jobRegisterService.searchJobRegister(searchJobRegisterVM, pageIndex, pageSize);
    }


@GetMapping("/link/{id}")
    public void downloadFiles(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
    JobRegister jobRegister= jobRegisterService.getDetailJR(id);
    String filename=jobRegister.getCv();
    File file=new File(filename);
    response.setContentType("application/octet-stream");

    String headerKey ="CONTENT_DISPOSITION";
    String headerValue="attachment; filename="+file.getName()+".pdf";
    response.setHeader(headerKey, headerValue);
    ServletOutputStream outputStream=response.getOutputStream();
    BufferedInputStream inputStream =new BufferedInputStream(new FileInputStream(file));
    byte[] buffer=new byte[8192];
    int bytesRead =-1;
    while ((bytesRead= inputStream.read(buffer))!=-1){
        outputStream.write(buffer, 0, bytesRead);
    }
    inputStream.close();
    outputStream.close();
    }


}