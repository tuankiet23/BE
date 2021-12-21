package com.itsol.recruit_managerment.controller;

import com.itsol.recruit_managerment.service.DashboardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashBoardControler {

    DashboardService dashboardService;

    @CrossOrigin
    @GetMapping("/statistics")
    public  Object getStastics(@RequestParam("from-date") String fromDate, @RequestParam("to-date")String toDate){

        return dashboardService.getStatistics(fromDate, toDate);
    }

}
