package com.example.crawlerbatchtest.infrastructure;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrawlerTarget {
    BASE(100),
    WOOWABRO(BASE.code + 1),
    KAKAO(BASE.code + 2),
    MARKET_KURLY(BASE.code + 3),
    NAVER(BASE.code + 4);
    private final long code;

}
