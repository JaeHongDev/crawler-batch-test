package com.example.crawlerbatchtest.config;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = TemporalTechBlogMigrationConfiguration.class)
class TemporalTechBlogMigrationConfigurationTest {

    // 실제로 빈을 등록하는 것 처럼 보이긴 함
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void 테스트() throws Exception {
        var jobParameters = new JobParametersBuilder()
                .addLong("techBlogCode", 101L)
                .toJobParameters();
        jobLauncherTestUtils.launchJob(jobParameters);
    }
}