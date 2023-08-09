package com.example.crawlerbatchtest.config;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail helloJobDetail() {
        return JobBuilder.newJob(QuartzBatchExecutor.class)
                .withIdentity("quartzBatchExecutor")
                .storeDurably()
                .build();
    }

    @Bean
    public SimpleTrigger helloJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(helloJobDetail())
                .withIdentity("helloTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .build();
    }
}