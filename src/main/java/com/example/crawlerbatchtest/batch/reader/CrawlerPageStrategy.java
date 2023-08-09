package com.example.crawlerbatchtest.batch.reader;

public enum CrawlerPageStrategy {
    // 단일 페이지 단위로 동작하는 전략
    SINGLE,
    // 페이지 단위로 발생하는 전략
    PAGE,
    SCROLL
}
