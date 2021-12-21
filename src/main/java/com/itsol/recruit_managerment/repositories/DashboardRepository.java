package com.itsol.recruit_managerment.repositories;

import java.util.Date;

public interface DashboardRepository {
    Object getStatistics(String fromDate, String toDate);
}
