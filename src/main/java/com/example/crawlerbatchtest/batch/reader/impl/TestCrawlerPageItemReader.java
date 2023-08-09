package com.example.crawlerbatchtest.batch.reader.impl;

import com.example.crawlerbatchtest.batch.reader.AbstractCrawlerPageItemReader;
import com.example.crawlerbatchtest.batch.reader.CrawlerPageStrategy;
import com.example.crawlerbatchtest.domain.ExternalPosts;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class TestCrawlerPageItemReader extends AbstractCrawlerPageItemReader {
    public TestCrawlerPageItemReader(WebDriver webDriver) {
        super(CrawlerPageStrategy.SINGLE, webDriver);
    }

    @Override
    protected ExternalPosts executeCrawlerPage() {
        log.info("아직 구현하지 않은 ItemReader 호출");
        return null;
    }


}
