package com.itsol.recruit_managerment.repositories.impl;

import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.repositories.*;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.repositories.jpa.MethodWorkRepoJPA;
import com.itsol.recruit_managerment.repositories.jpa.StatusJobRepoJPA;
import com.itsol.recruit_managerment.service.impl.JobServiceimpl;
import com.itsol.recruit_managerment.utils.BaseRepository;
import com.itsol.recruit_managerment.utils.CommonConst;
import com.itsol.recruit_managerment.utils.SqlReader;
import com.itsol.recruit_managerment.vm.SearchJobVM;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class JobRepoImpl extends BaseRepository implements JobRepository {

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
    public List<Job> getListJobWithConditonInHomePage(Object obj) {
        List<Job> result = null;
        try {
            String sql = "select Jobs.* from status_job INNER JOIN Jobs ON JOBS.status_job_id = status_job.id where JOBs.is_delete = 0 and  (SYSDATE - JOBs.create_date) < :p_numberDay and (status_job.NAME NOT LIKE  'J_STOP' AND  status_job.NAME NOT LIKE  'J_WAIT' AND status_job.NAME NOT LIKE  'J_CLOSED')";
            result = getSession().createSQLQuery(sql)
                    .setParameter("p_numberDay",CommonConst.DAY_OF_NEW_JOB)
                    .addScalar("id", new IntegerType())

                    .setResultTransformer(new AliasToBeanResultTransformer(Job.class)).list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public List<Job> serchJob(SearchJobVM searchJobVM, Integer pageSize, Integer pageIndex) {
            try {
                String query = SqlReader.getSqlQueryById(SqlReader.ADMIN_MODULE_JOB, "searchjob");
                Map<String, Object> parameters = new HashMap<>();

                if (!ObjectUtils.isEmpty(searchJobVM.getJobName())) {
                    String jobName=searchJobVM.getJobName().toUpperCase();

                    query += " and UPPER(jobs.job_name) like :p_job_name";
                    parameters.put("p_job_name","%"+jobName+"%");
                }
                if (!ObjectUtils.isEmpty(searchJobVM.getSalaryMax())) {
                    query += " and jobs.salary_max < :p_salary_max";
                    parameters.put("p_salary_max",searchJobVM.getSalaryMax());
                }
                if (!ObjectUtils.isEmpty(searchJobVM.getSalaryMin())) {
                    query += " and jobs.salary_min > :p_salary_min";
                    parameters.put("p_salary_min",searchJobVM.getSalaryMin());
                }

                Integer p_startrow;
                Integer p_endrow;

                if(pageIndex==0)
                {
                    p_startrow=pageSize*pageIndex;
                    p_endrow=p_startrow+pageSize;
                }
                else {
                    p_startrow=pageSize*pageIndex-pageSize+1;
                    p_endrow=p_startrow+pageSize-1;
                }

                query += " ) tabWithRownum where tabWithRownum.ROWNR BETWEEN  :p_startrow and :p_endrow  ";
                parameters.put("p_startrow", p_startrow);
                parameters.put("p_endrow", p_endrow);
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



