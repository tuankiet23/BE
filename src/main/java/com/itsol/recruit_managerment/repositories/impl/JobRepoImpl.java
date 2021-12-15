package com.itsol.recruit_managerment.repositories.impl;

import com.itsol.recruit_managerment.model.Job;
import com.itsol.recruit_managerment.repositories.JobRepository;
import com.itsol.recruit_managerment.repositories.jpa.JobRepoJPA;
import com.itsol.recruit_managerment.utils.BaseRepository;
import com.itsol.recruit_managerment.utils.CommonConst;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.IntegerType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class JobRepoImpl extends BaseRepository implements JobRepository {

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
}
