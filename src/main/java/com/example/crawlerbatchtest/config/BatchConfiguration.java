package com.example.crawlerbatchtest.config;


import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import com.example.crawlerbatchtest.batch.entity.TemporalTechBlogEntity;
import com.example.crawlerbatchtest.batch.entity.TemporalTechBlogEntityRepository;
import com.example.crawlerbatchtest.batch.reader.CrawlerPageItemReaderFactory;
import com.example.crawlerbatchtest.domain.ExternalPosts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CrawlerPageItemReaderFactory crawlerPageItemReaderFactory;
    private final TemporalTechBlogEntityRepository temporalTechBlogEntityRepository;

    private final WebDriver webDriver;

    @Bean
    @JobScope
    public Step webCrawlingTaskletStep(
            @Value("#{jobParameters[techBlogCode]}") Long code
    ) {

        var crawlerGroup = CrawlerGroup.valueOf(code);

        return new StepBuilder("webCrawlingTaskletStep", jobRepository)
                .<ExternalPosts, ExternalPosts>chunk(1, transactionManager)
                .reader(crawlerPageItemReaderFactory.createPageItemReader(crawlerGroup).apply(webDriver))
                .writer((chunk) -> {
                    var temporalTechBlogEntities = chunk.getItems()
                            .stream()
                            .flatMap(externalPosts -> externalPosts.posts()
                                    .stream()
                                    .map(post -> TemporalTechBlogEntity.builder()
                                            .title(post.title())
                                            .author(post.author())
                                            .summary(post.summary())
                                            .thumbnailUrl(post.thumbnailUrl())
                                            .url(post.link())
                                            .createdDate(post.postDate())
                                            .crawlerGroup(post.crawlerGroup())
                                            .urlSuffix(post.suffix()).build())
                            ).toList();
                    temporalTechBlogEntityRepository.saveAll(temporalTechBlogEntities);
                    log.info("save success");
                })
                .build();
    }


    @Bean(name = "webCrawlingJob")
    public Job webCrawlingJob() {
        return new JobBuilder("webCrawlingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(webCrawlingTaskletStep(null))
                .build();
    }


}
