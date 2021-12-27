package com.itsol.recruit_managerment;

import com.itsol.recruit_managerment.service.UploadCVService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;

@SpringBootApplication
@EnableScheduling
public class RecruitManagermentApplication implements CommandLineRunner {
    @Resource
    UploadCVService uploadCVService;

    public static void main(String[] args) {
        SpringApplication.run(RecruitManagermentApplication.class, args);
    }
    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
    @Override
    public void run(String... arg) throws Exception {
        System.out.println("create root directory root to upload cv");
//        uploadCVService.deleteAll();
        uploadCVService.init();
    }

}
