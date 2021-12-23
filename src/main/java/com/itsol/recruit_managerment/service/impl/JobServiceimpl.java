package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.dto.JobHomeDTO;
import com.itsol.recruit_managerment.model.*;

import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.repositories.jpa.MethodWorkRepoJPA;
import com.itsol.recruit_managerment.repositories.jpa.StatusJobRepoJPA;
import com.itsol.recruit_managerment.service.JobService;


import com.itsol.recruit_managerment.utils.SqlReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class JobServiceimpl extends BaseRepository implements  JobService  {

    JobRepoJPA jobRepo;

    UserJobRepositoryImpl userJobRepository;

    @Autowired
    AcademiclevelRepo academiclevelRepo;

    @Autowired
    IUserRespository iUserRespository;

    @Autowired
    JobPositionRepo jobPositionRepo;

    @Autowired
    LevelRankRepo levelRankRepo;

    @Autowired
    MethodWorkRepoJPA methodWorkRepoJPA;

    @Autowired
    StatusJobRepoJPA statusJobRepoJPA;

    @Override
    public List<Job> getAllJob() {
        return jobRepo.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        try {
            Job job= jobRepo.getJobById(id);
            Integer view= job.getViews() +1;
            jobRepo.updateView(id, view);
            return job;
        }catch (Exception e){
          log.error("failed get by id: " + id);
            return null;
        }
    }

    @Override
    public List<Job> getListNewJobs(Integer numberDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jobRepo.getListNewJobs(numberDate,  pageable);
    }

    @Override
    public List<Job> getListJobsHightSalary(Integer salary, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepo.getListJobsHightSalary(salary,  pageable);
    }

    @Override
    public List<Job> getListJobDeadLine(Integer numberDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepo.getListJobsDeadLine(numberDate, pageable);
    }

    @Override
    public List<Job> getJobsWithCondition(int modJob, int startRow, int endRow) {
        return userJobRepository.getListJobWithCondition(modJob, startRow, endRow) ;
    }

    @Override
    public JobHomeDTO jobHome() {
        return null;
    }

    @Override
    public List<JobRegister> searchJob(Integer pageIndex, Integer pageSize) {
        try {
            String query = SqlReader.getSqlQueryById(SqlReader.ADMIN_MODULE_JOB, "searchjob");
            Map<String, Object> parameters = new HashMap<>();
            Integer p_startrow;
            Integer p_endrow;
            if(pageIndex==0)
            {
                p_startrow=pageSize*pageIndex;
                p_endrow=p_startrow+pageSize;
            }
            else {
                p_startrow=pageSize*pageIndex+1;
                p_endrow=p_startrow+pageSize-1;
            }

            query += " ) tabWithRownum where tabWithRownum.ROWNR BETWEEN  :p_startrow and :p_endrow  ";
            parameters.put("p_startrow", p_startrow);
            parameters.put("p_endrow", p_endrow);
return null;
        //    return getNamedParameterJdbcTemplate().query(query, parameters, new JobMapper());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    class JobMapper implements RowMapper<Job> {
        public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
            Job dto = new Job();
            dto.setJobName(rs.getString("job_name"));
            dto.setCreateDate(rs.getDate("create_date"));
            dto.setNumberExperience(rs.getInt("number_experience"));
            dto.setAddressWork(rs.getString("address_work"));
            dto.setDescription(rs.getString("description"));
            dto.setDueDate(rs.getDate("due_date"));
            dto.setStartRecruitmentDate(rs.getDate("start_recruitment_date"));
            dto.setInterrest(rs.getString("interrest"));
            dto.setSalary(rs.getInt("salary"));
            dto.setSkills(rs.getString("skills"));
            dto.setQtyPerson(rs.getInt("qty_person"));
            dto.setViews(rs.getInt("views"));
            dto.setId(rs.getLong("id"));

            User user = new User();
            user.setId(rs.getLong("create_id"));
            dto.setCreater(iUserRespository.findById(user.getId()).get());

            AcademicLevel academicLevel = new AcademicLevel();
            academicLevel.setId(rs.getLong("academic_level_id"));
            AcademicLevel newAcademicLevel = academiclevelRepo.findById(academicLevel.getId()).get();
            dto.setAcademicLevel(newAcademicLevel);

            User userContact = new User();
            userContact.setId(rs.getLong("contact_id"));
            User newContact = iUserRespository.findById(userContact.getId()).get();
            dto.setContact(newContact);

            JobPosition jobPosition = new JobPosition();
            jobPosition.setId(rs.getLong("job_position_id"));
            JobPosition newJobPosition = jobPositionRepo.findById(jobPosition.getId()).get();
            dto.setJobPosition(newJobPosition);

            LevelRank levelRank = new LevelRank();
            levelRank.setId(rs.getLong("level_id"));
            LevelRank newLevelRank = levelRankRepo.findById(levelRank.getId()).get();
            dto.setLevelRank(newLevelRank);

            MethodWork methodWork = new MethodWork();
            methodWork.setId(rs.getLong("method_work_id"));
            MethodWork newMethodWork = methodWorkRepoJPA.findById(methodWork.getId()).get();
            dto.setMethod_work(newMethodWork);

            StatusJob statusJob = new StatusJob();
            statusJob.setId(rs.getLong("status_job_id"));
            StatusJob newStatusJob = statusJobRepoJPA.findById(statusJob.getId()).get();
            dto.setStatusJob(newStatusJob);
            return dto;
        }
    }

}
