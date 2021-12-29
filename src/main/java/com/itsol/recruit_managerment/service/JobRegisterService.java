package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.dto.AdminJobRegisterDTO;
import com.itsol.recruit_managerment.dto.JobRegisterDTO;
import com.itsol.recruit_managerment.model.JobRegister;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.vm.JobRegisterVM;
import com.itsol.recruit_managerment.vm.SearchJobRegisterVM;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface JobRegisterService {
    List<JobRegister> getAllJobRegister();
    Page<JobRegister> getAll(Integer pageindex, Integer pagesize);
    Boolean delete(Long id);
    Boolean save(JobRegisterVM jobRegisterVM);
    AdminJobRegisterDTO getJobRegisterById(Long id);
    List<JobRegister> getAllJR();
    JobRegister getDetailJR(Long id);
    List<JobRegisterDTO> searchJobRegister(SearchJobRegisterVM searchJobRegisterVM, Integer pageIndex, Integer pageSize);
    Resource downloadCv(Long applicantId) throws IOException;
    String getCvFileName(String cvFilePath);
}
