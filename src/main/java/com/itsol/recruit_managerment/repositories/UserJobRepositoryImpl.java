package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.*;
import com.itsol.recruit_managerment.utils.CommonConst;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.utils.SqlReader;
import com.itsol.recruit_managerment.vm.JobVM;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<Job> searchJobs(int modJob) {
        try {
            String query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_job_home");
            Map<String, Object> parameters = new HashMap<>();

//            int modNewJob = ModJob.NEW_JOB.getValue();
            if (modJob == ModJob.HIGHT_SALARY.getValue()) {
                int salaryCompare = CommonConst.HIGHT_SALARY_VALUE;
                query += " and JOBs.salary >= :p_salary_compare";
                parameters.put("p_salary_compare", 18000000);

            }
            if (modJob == ModJob.NEW_JOB.getValue()) {
                Integer numberDate = CommonConst.DAY_OF_NEW_JOB;
                query += "and (SYSDATE - JOBs.create_date) <=  :p_number_day";
                parameters.put("p_number_day", numberDate);

            }
            return getNamedParameterJdbcTemplate().query(query, parameters, new JobMapper());

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
            // tương tự với các trường khác
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
//            dto.setDelete(rs.getBoolean("is_delete"));
            User user = new User();
            user.setId(rs.getLong("id"));
            dto.setCreater(user);

            AcademicLevel academicLevel = new AcademicLevel();
            academicLevel.setId(rs.getLong("id"));
            dto.setAcademicLevel(academicLevel);

            User userContact = new User();
            userContact.setId(rs.getLong("id"));
            dto.setContact(userContact);

            JobPosition jobPosition = new JobPosition();
            jobPosition.setId(rs.getLong("id"));
            dto.setJobPosition(jobPosition);

            LevelRank levelRank = new LevelRank();
            levelRank.setId(rs.getLong("id"));
            dto.setLevelRank(levelRank);

            MethodWork methodWork = new MethodWork();
            methodWork.setId(rs.getLong("id"));
//            methodWork.setMethod_name(rs.getString("method_name"));
            dto.setMethod_work(methodWork);

            StatusJob statusJob = new StatusJob();
            statusJob.setId(rs.getLong("id"));
            dto.setStatusJob(statusJob);
            return dto;
        }
    }

}
