package com.example.crawlerbatchtest.batch.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class TechBlogRepositoryTest {
    @Autowired
    private TechBlogRepository techBlogRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void 기술블로그에_중복된_데이터가_존재하는지_아닌지_여부를_검사한다() {
        final var id = "/1234";
        techBlogRepository.save(TechBlogEntity.builder()
                .title("title")
                .crawlerGroup(CrawlerGroup.NAVER)
                .urlSuffix(id)
                .summary("summary")
                .thumbnailUrl("thumbnail")
                .createdDate(LocalDate.now())
                .author("")
                .url("/")
                .build());

        assertThat(techBlogRepository.existsByCrawlerGroupAndUrlSuffix(CrawlerGroup.NAVER, id)).isTrue();
    }

    @Test
    void 기술블로그에_중복된_데이터가_존재하지_않으면_거짓을_반환한다() {
        final var id = "/12345";
        techBlogRepository.save(TechBlogEntity.builder()
                .title("title")
                .crawlerGroup(CrawlerGroup.NAVER)
                .urlSuffix(id)
                .summary("summary")
                .thumbnailUrl("thumbnail")
                .createdDate(LocalDate.now())
                .author("")
                .url("/")
                .build());

        assertThat(techBlogRepository.existsByCrawlerGroupAndUrlSuffix(CrawlerGroup.NAVER, "/1234")).isFalse();
    }


}