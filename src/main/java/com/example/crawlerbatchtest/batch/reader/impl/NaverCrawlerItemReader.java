package com.example.crawlerbatchtest.batch.reader.impl;

import com.example.crawlerbatchtest.batch.config.CrawlerGroup;
import com.example.crawlerbatchtest.batch.reader.AbstractCrawlerPageItemReader;
import com.example.crawlerbatchtest.batch.reader.CrawlerPageStrategy;
import com.example.crawlerbatchtest.domain.ExternalPost;
import com.example.crawlerbatchtest.domain.ExternalPosts;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class NaverCrawlerItemReader extends AbstractCrawlerPageItemReader {
    private static final String TARGET_URL = "https://d2.naver.com/home";

    // 네이버의 경우 크롤링 페이지의 위치가 news, helloworld 이렇게 2개의 페이지가 존재하기 때문에 별도의 domain만 prefix를 가지도록 설정
    private static final String PREFIX_URL = "https://d2.naver.com/";
    private static final CrawlerGroup GROUP = CrawlerGroup.NAVER;

    public NaverCrawlerItemReader(
            WebDriver webDriver) {
        super(CrawlerPageStrategy.PAGE, webDriver);
    }

    @Override
    protected ExternalPosts executeCrawlerPage() {
        log.info("start crawler naver blog");
        this.selectPage();

        return new ExternalPosts(this.webDriver.findElements(By.className("post_article"))
                .stream()
                .map(webElement -> {
                    final var postElement = webElement.findElement(By.className("cont_post"));

                    final var titleElement = postElement.findElement(By.tagName("h2"))
                            .findElement(By.tagName("a"));
                    // naver post title
                    final var url = titleElement.getAttribute("href");
                    final var title = titleElement.getText();

                    // naver post body
                    final var thumbnail = postElement.findElement(By.className("cont_img"))
                            .findElement(By.tagName("img"))
                            .getAttribute("src");
                    final var summary = postElement.findElement(By.className("post_txt"))
                            .getText();
                    final var postDate = postElement.findElement(By.tagName("dl"))
                            .findElement(By.tagName("dd"))
                            .getText();

                    return ExternalPost.builder()
                            .link(PREFIX_URL)
                            .suffix(url.substring(PREFIX_URL.length()))
                            .title(title)
                            .author("") // naver는 작성자가 없음
                            .summary(summary)
                            .thumbnailUrl(thumbnail)
                            .postDate(LocalDate.parse(postDate, FORMATTER1))
                            .crawlerGroup(GROUP)
                            .build();
                }).toList());
    }


    @Override
    protected String getPageUrlByParameter(int page) {
        final var url = TARGET_URL + "?page=" + page;
        log.info("crawler naver url: {}", url);
        return url;
    }


    @Override
    protected int getLastPage() {
        return this.webDriver.findElements(By.className("btn_num"))
                .stream()
                .map(webElement -> webElement.getAttribute("data-number"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElseThrow(IllegalArgumentException::new);
    }
}
