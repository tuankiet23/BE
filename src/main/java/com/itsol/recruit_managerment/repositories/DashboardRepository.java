package com.itsol.recruit_managerment.repositories;

import org.springframework.stereotype.Repository;

import java.util.Date;

public interface DashboardRepository {
    Object getStatistics(String fromDate, String toDate);
    Object getPieChar(String fromDate, String toDate);
    Object getLineChart(String typeTime, String fromDate, String toDate);
}
