package com.itsol.recruit_managerment.service;

import java.util.Date;

public interface DashboardService {
    Object getStatistics(String formDate, String toDate);
    Object getPieChart(String formDate, String toDate);
    Object getLineChart(String typeTime, String formDate, String toDate);
}
