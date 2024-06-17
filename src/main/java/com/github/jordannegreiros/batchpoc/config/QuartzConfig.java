package com.github.jordannegreiros.batchpoc.config;

import com.github.jordannegreiros.batchpoc.job.ProcessaClientesSchedulerJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder
                .newJob(ProcessaClientesSchedulerJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quartzTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(10)
                .withRepeatCount(1);

        return TriggerBuilder
                .newTrigger()
                .forJob(quartzJobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }
}
