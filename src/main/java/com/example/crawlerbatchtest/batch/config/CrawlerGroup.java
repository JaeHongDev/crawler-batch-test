package com.example.crawlerbatchtest.batch.config;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrawlerGroup {
    BASE(100L),
    WOOWABRO(BASE.code + 1),
    KAKAO(BASE.code + 2),
    MARKET_KURLY(BASE.code + 3),
    NAVER(BASE.code + 4);
    private final Long code;


    public static CrawlerGroup valueOf(Long code) {
        return Arrays.stream(values())
                .filter(crawlerGroup -> Objects.equals(crawlerGroup.code, code))
                .findFirst()
                .orElse(BASE);

    }
}
