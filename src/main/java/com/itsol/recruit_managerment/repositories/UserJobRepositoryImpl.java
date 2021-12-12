package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.*;
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
    public List<Job> searchJobs(JobVM jobVM) {
        try {
            String query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_job_home");
            Map<String, Object> parameters = new HashMap<>();
//            if (!ObjectUtils.isEmpty(jobVM.getJobName())) {
//                query += "and job_name like :p_name";
//                parameters.put("p_name", jobVM.getJobName());
//            }
//            if (!ObjectUtils.isEmpty(jobVM.getQtyPerson().toString())) {
//                query += "and qty_person > :p_quantity_person";
//                parameters.put("p_quantity_person", jobVM.getQtyPerson());
//            }

//            return getNamedParameterJdbcTemplate().query(query, parameters, new JobMapper());
            return getNamedParameterJdbcTemplate().query(query, new JobMapper());
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
            User user = rs.getObject("creater",User.class);
            dto.setCreater(user);
            dto.setAcademicLevel(rs.getObject("academic_level_id", AcademicLevel.class));
            dto.setContact(rs.getObject("contact_id", User.class));
            dto.setJobPosition(rs.getObject("job_position_id", JobPosition.class));
            dto.setLevelRank(rs.getObject("level_id", LevelRank.class));
            dto.setMethod_work(rs.getObject("method_work", MethodWork.class));
            dto.setStatusJob(rs.getObject("status_job_id", StatusJob.class));
            return dto;
        }
    }

}
