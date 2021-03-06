package com.itsol.recruit_managerment.service.impl;

import com.itsol.recruit_managerment.repositories.DashboardRepository;
import com.itsol.recruit_managerment.service.DashboardService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@AllArgsConstructor
public class DashBoardSeviceImpl implements DashboardService {

    DashboardRepository dashboardRepository;

    @Override
    public Object getStatistics(String formDate, String toDate) {
        return dashboardRepository.getStatistics( formDate,  toDate);
    }

    @Override
    public Object getPieChart(String formDate, String toDate) {
        return dashboardRepository.getPieChar(formDate,toDate);
    }

    @Override
    public Object getLineChart(String typeTime, String formDate, String toDate) {
        return dashboardRepository.getLineChart(typeTime,formDate,toDate);
    }
}
