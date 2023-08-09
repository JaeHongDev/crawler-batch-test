package com.example.crawlerbatchtest.batch.entity;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemporalTechBlogEntityRepository extends JpaRepository<TemporalTechBlogEntity, Long> {

    @Query("SELECT T FROM TemporalTechBlogEntity T WHERE T.crawlerGroup = :crawlerGroup")
    Optional<TemporalTechBlogEntity> findA(CrawlerGroup crawlerGroup);
}