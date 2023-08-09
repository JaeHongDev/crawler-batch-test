package com.example.crawlerbatchtest.config;

import com.example.crawlerbatchtest.infrastructure.CrawlerTarget;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
public class QuartzBatchExecutor extends QuartzJobBean {
    private final JobLauncher jobLauncher;
    private final Job crawlingJob;
    private final Job migrationJob;

    public QuartzBatchExecutor(JobLauncher jobLauncher,
                               @Qualifier("webCrawlingJob") Job crawlingJob,
                               @Qualifier("migrationJob") Job migrationJob) {
        this.jobLauncher = jobLauncher;
        this.crawlingJob = crawlingJob;
        this.migrationJob = migrationJob;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Arrays.stream(CrawlerTarget.values())
                .forEach(this::executeJobBy);
    }

    @SneakyThrows
    private void executeJobBy(CrawlerTarget crawlerTarget) {
        log.info("execute job by {}", crawlerTarget);
        //crawling(crawlerTarget);
        migration(crawlerTarget);
    }

    @SneakyThrows
    private void crawling(CrawlerTarget crawlerTarget) {
        jobLauncher.run(crawlingJob, new JobParametersBuilder()
                .addLong("techBlogCode", crawlerTarget.getCode())
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }

    @SneakyThrows
    private void migration(CrawlerTarget crawlerTarget) {
        jobLauncher.run(migrationJob, new JobParametersBuilder()
                .addLong("techBlogCode", crawlerTarget.getCode())
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }
}
