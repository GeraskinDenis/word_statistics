package ru.geraskindenis.word_statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geraskindenis.word_statistics.db.StatisticsRecord;
import ru.geraskindenis.word_statistics.db.repository.StatisticsRecordRep;

import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping(value = "", produces = "application/json")
public class WordStatisticsApplication {

    @Autowired
    private StatisticsRecordRep statisticsRecord;

    public static void main(String[] args) {

        String url = "";

        if (args.length != 1) {
            url = "https://www.simbirsoft.com/";
        } else {
            url = args[0];
        }

        SpringApplication.run(WordStatisticsApplication.class, args);

        String text = HTMLParser.getText(url);
        // Удаляем все знаки препинания, телефоны, даты
        String tmp = "[,\"«»()©'\\[\\]:%]|\\s-\\s|\\.(?=\\s)|\\d{2}\\.\\d{2}\\.\\d{4}|(?<=\\s)\\d+(?=\\s)|\\d-\\d{3}-\\d{3}-\\d{4}|\\.$|—";
        text = text.replaceAll(tmp, "");
        Map<String, Integer> statistics = WordStatistics.getNumberOfRepetitions(text);

        // Вывод в консоль статистики
//        System.out.println(statistics);

        // Запись статистики в БД
    }

    @RequestMapping("create-statistics_record")
    public StatisticsRecord createStatisticsRecord() {
        return statisticsRecord.save(new StatisticsRecord());
    }
}
