package com.itsol.recruit_managerment.repositories.impl;

import com.itsol.recruit_managerment.repositories.DashboardRepository;
import com.itsol.recruit_managerment.repositories.UserJobRepositoryImpl;
import com.itsol.recruit_managerment.utils.BaseRepository;
import com.itsol.recruit_managerment.utils.CommonConst;
import com.itsol.recruit_managerment.utils.ModJob;
import com.itsol.recruit_managerment.utils.SqlReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Repository
public class DashboardRepositoryImpl extends BaseRepository implements DashboardRepository {


    @Override
    public Object getStatistics(String fromDate, String toDate) {
        System.out.println("from date: " + fromDate);
        System.out.println("to date: " + toDate);
        try {
            String query = SqlReader.getSqlQueryById(SqlReader.ADMIN_DASHBOARD_MODULE, "dashboard_statistics");
            Map<String, Object> parameters = new HashMap<>();
                parameters.put("p_from_date", fromDate);
                parameters.put("p_to_date", toDate);
            } catch (Exception e) {
           log.error(e.getMessage());
        }
        return null;
    }
}
