package com.example.crawlerbatchtest.domain;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ExternalPost(
        String link,
        String title,
        String summary,
        String author,
        LocalDate postDate,
        String suffix,
        String thumbnailUrl,
        CrawlerGroup crawlerGroup) {
}
