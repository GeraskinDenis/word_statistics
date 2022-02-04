package ru.geraskindenis.word_statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geraskindenis.word_statistics.db.StatisticsRecord;
import ru.geraskindenis.word_statistics.db.repository.StatisticsRecordRep;

import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping(value = "", produces = "application/json")
public class WordStatisticsApplication {

    @Autowired
    private StatisticsRecordRep statisticsRecord;

    private static final Logger LOGGER = LoggerFactory.getLogger(WordStatisticsApplication.class);

    public static void main(String[] args) {

        LOGGER.info("Before Starting application");
        SpringApplication.run(WordStatisticsApplication.class, args);
        LOGGER.info("Starting application with {} arguments.", args.length);

        String url;
        if (args.length != 1) {
            url = "https://www.simbirsoft.com/";
        } else {
            url = args[0];
        }


        // Получаем текст с веб-страницы
        String text = null;
        try {
            LOGGER.info("Get text from url: " + url);
            text = HTMLParser.getText(url);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return;
        }

        // Удаляем все знаки препинания, телефоны, даты
        LOGGER.info("Clear text...");
        String regex = "[,\"«»()©'\\[\\]:%]|\\s-\\s|\\.(?=\\s)|\\d{2}\\.\\d{2}\\.\\d{4}|(?<=\\s)\\d+(?=\\s)|\\d-\\d{3}-\\d{3}-\\d{4}|\\.$|—";
        text = text.replaceAll(regex, "");

        // Получаем статистику по количеству уникальных слов
        LOGGER.info("Getting statistics...");
        Map<String, Integer> statistics = WordStatistics.getNumberOfRepetitions(text);

        // Отображаем статистику
        StringBuilder builder = new StringBuilder("===Start of report===\n");
        statistics.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(set -> {
                    builder.append(set.getKey())
                            .append(" - ")
                            .append(set.getValue())
                            .append("\n");
                });
        builder.append("===End of report===");
        LOGGER.info(builder.toString());

        // Запись статистики в БД
    }

    @RequestMapping("create-statistics_record")
    public StatisticsRecord createStatisticsRecord() {
        return statisticsRecord.save(new StatisticsRecord());
    }
}
