package com.itsol.recruit_managerment.repositories.impl;

import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.repositories.jpa.MethodWorkRepoJPA;
import com.itsol.recruit_managerment.repositories.jpa.StatusJobRepoJPA;
import com.itsol.recruit_managerment.utils.CommonConst;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.utils.SqlReader;
import com.itsol.recruit_managerment.vm.SearchJobVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserJobRepositoryImpl extends BaseRepository implements UserJobRepository {
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
    public List<Job> getListJobWithCondition(int modJob, int pageNumber, int pageSize) {
        try {

            String query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_job_home");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("pageNumber", pageNumber);
            parameters.put("pageSize", pageSize);
            if (modJob == ModJob.DEAD_LINE.getValue()) {
                query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_job_dead_line");
                Integer numberDay = CommonConst.DAY_OF_DEADLINE;
                parameters.put("p_numberDay", numberDay);
            }

            if (modJob == ModJob.HIGHT_SALARY.getValue()) {
                int salaryCompare = CommonConst.HIGHT_SALARY_VALUE;

                query += " and tblJobs.salary_max >= :p_salary_compare";

                parameters.put("p_salary_compare", 18000000);
            }

            if (modJob == ModJob.NEW_JOB.getValue()) {
                Integer numberDate = CommonConst.DAY_OF_NEW_JOB;
                query += "and (SYSDATE - tblJobs.create_date) <=  :p_number_day";
                parameters.put("p_number_day", numberDate);
            }

            return getNamedParameterJdbcTemplate().query(query, parameters, new JobMapper());

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Job> searchJobHomePage(SearchJobVM searchJobVM, int pageNumber, int pageSize) {
        try{
            String query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_job_home");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("pageNumber", pageNumber);
            parameters.put("pageSize", pageSize);
            if(ObjectUtils.isEmpty(searchJobVM.getJobName()) && ObjectUtils.isEmpty(searchJobVM.getNumberExperence())
                    && ObjectUtils.isEmpty(searchJobVM.getJobPosition()) ){
                return null;
            }
            if(!ObjectUtils.isEmpty(searchJobVM.getJobName()) ){
                query += "and tblJobs.job_name like :p_job_name";
                parameters.put("p_job_name", "%"+searchJobVM.getJobName()+"%");
            }
            if(!ObjectUtils.isEmpty(searchJobVM.getNumberExperence())){
                query += " and tblJobs.number_experience like :p_number_experience";
                parameters.put("p_number_experience", searchJobVM.getNumberExperence());
            }
            if(!ObjectUtils.isEmpty(searchJobVM.getJobPosition())){
                query += " and tblJobs.job_position_id like :p_job_position ";
                parameters.put("p_job_position", jobPositionRepo.findByName(searchJobVM.getJobPosition()).getId());
            }

            return getNamedParameterJdbcTemplate().query(query, parameters, new JobMapper());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
            return null;
    }

    class JobMapper implements RowMapper<Job> {

        @Override
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
            dto.setSalaryMax(rs.getInt("salary_max"));
            dto.setSalaryMin(rs.getInt("salary_min"));
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
