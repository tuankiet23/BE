//package com.itsol.recruit_managerment.repositories;
//
//import com.itsol.recruit_managerment.model.Job;
//import com.itsol.recruit_managerment.model.JobRegister;
//import com.itsol.recruit_managerment.utils.SqlReader;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.annotations.NamedQuery;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.NamedNativeQuery;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Slf4j
//@Repository
//public class JobRegisterRepositoryImpl extends BaseRepository implements JobRegisterRepo  {
//    @Override
//    public List<JobRegister> getAll() {
//        try {
//            String query = SqlReader.getSqlQueryById(SqlReader.USER_HOME_MODULE, "list_jobregister");
//            return jdbcTemplate.query(
//                    query, new JobRegisterMapper()
//            );
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//        return null;
//    }
//
//    class JobRegisterMapper implements RowMapper<JobRegister> {
//        public JobRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
//            JobRegister dto = new JobRegister();
//            dto.setId(rs.getLong("id"));
//            dto.setCv(rs.getString("CV_file"));
//
//            // tương tự với các trường khác
//            return dto;
//        }
//    }
//
//}
