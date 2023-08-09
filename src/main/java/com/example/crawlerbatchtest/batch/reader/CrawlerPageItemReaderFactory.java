package com.example.crawlerbatchtest.batch.reader;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import com.example.crawlerbatchtest.batch.reader.impl.MarketKurlyItemReader;
import com.example.crawlerbatchtest.batch.reader.impl.NaverCrawlerItemReader;
import com.example.crawlerbatchtest.batch.reader.impl.TestCrawlerPageItemReader;
import java.util.function.Function;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class CrawlerPageItemReaderFactory {

    public Function<WebDriver, AbstractCrawlerPageItemReader> createPageItemReader(CrawlerGroup crawlerGroup) {
        return switch (crawlerGroup) {
            case BASE, WOOWABRO, KAKAO -> TestCrawlerPageItemReader::new;
            case MARKET_KURLY -> MarketKurlyItemReader::new;
            case NAVER -> NaverCrawlerItemReader::new;
        };
    }

}
