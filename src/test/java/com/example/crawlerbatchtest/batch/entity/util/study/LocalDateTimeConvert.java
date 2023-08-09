package com.example.crawlerbatchtest.batch.entity.util.study;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class LocalDateTimeConvert {


    @Test
    void 문자열에서_localDateTime으로_변경() {
        var input = "2023.03.02";

        var basicPattern = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        var result = LocalDate.parse(input, basicPattern);
        System.out.println(result);

        input = "2023.03.02.";

        basicPattern = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        result = LocalDate.parse(input, basicPattern);
        System.out.println(result);


    }
}
