package com.example.crawlerbatchtest.config;


import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import com.example.crawlerbatchtest.batch.entity.TechBlogEntity;
import com.example.crawlerbatchtest.batch.entity.TechBlogRepository;
import com.example.crawlerbatchtest.batch.entity.TemporalTechBlogEntity;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TemporalTechBlogMigrationConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final TechBlogRepository techBlogRepository;

    @Bean(name = "migrationJob")
    public Job migrationJob() {
        return new JobBuilder("migrationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(migrationStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step migrationStep(
            @Value("#{jobParameters[techBlogCode]}") Long code
    ) {
        final var crawlerGroup = CrawlerGroup.valueOf(code);
        return new StepBuilder("migrationStep", jobRepository)
                .<TemporalTechBlogEntity, TemporalTechBlogEntity>chunk(100, transactionManager)
                .reader(new JpaCursorItemReaderBuilder<TemporalTechBlogEntity>()
                        .name("migrationJbcItemReader")
                        .entityManagerFactory(entityManagerFactory)
                        .queryString("SELECT T FROM TemporalTechBlogEntity T WHERE T.crawlerGroup = :crawlerGroup")
                        .parameterValues(new HashMap<>() {{
                            this.put("crawlerGroup", crawlerGroup);
                        }})
                        .build())
                .writer(chunk -> {
                    chunk.getItems()
                            .stream()
                            .filter(temporalTechBlogEntity -> !this.techBlogRepository.existsByCrawlerGroupAndUrlSuffix(
                                    temporalTechBlogEntity.getCrawlerGroup(),
                                    temporalTechBlogEntity.getUrlSuffix()))
                            .map(TechBlogEntity::from)
                            .forEach(this.techBlogRepository::save);
                })
                .build();
    }

}
