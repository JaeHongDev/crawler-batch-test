package com.example.crawlerbatchtest.batch.entity;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechBlogRepository extends JpaRepository<TechBlogEntity, Long> {

    boolean existsByCrawlerGroupAndUrlSuffix(CrawlerGroup crawlerGroup, String urlSuffix);

    default boolean doesNotExistsByCrawlerGroupAndUrlSuffix(CrawlerGroup crawlerGroup, String urlSuffix) {
        return !this.existsByCrawlerGroupAndUrlSuffix(crawlerGroup, urlSuffix);
    }
}
