package ru.geraskindenis.word_statistics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geraskindenis.word_statistics.db.Person;
import ru.geraskindenis.word_statistics.db.StatisticsRecord;
import ru.geraskindenis.word_statistics.db.repository.PersonRep;
import ru.geraskindenis.word_statistics.db.repository.StatisticsRecordRep;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping(value = "", produces = "application/json")
public class WordStatisticsApplication {

    @Autowired
    private PersonRep personRep;

    @Autowired
    private StatisticsRecordRep statisticsRecord;

    public static void main(String[] args) {
        SpringApplication.run(WordStatisticsApplication.class, args);

        // Получаем текст из интернета
        String html = "https://www.simbirsoft.com/";
        Document document;
        try {
            document = Jsoup.connect(html).get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String text = document.text();

        // Удаляем все знаки препинания, телефоны, даты
        String tmp = "[,\"«»()©'\\[\\]:%]|\\s-\\s|\\.(?=\\s)|\\d{2}\\.\\d{2}\\.\\d{4}|(?<=\\s)\\d+(?=\\s)|\\d-\\d{3}-\\d{3}-\\d{4}|\\.$|—";
        String text2 = text.replaceAll(tmp, "");
//        System.out.println(text2);

        // Получаем массив слов
        String[] strings = text2.split("\\s+");
//		Arrays.stream(strings).forEach(System.out::println);

        // Получаем статистику
        Map<String, Integer> map = new HashMap<>();
        for (String s : strings) {
            Integer integer = map.get(s);
            map.put(s, (integer == null) ? 1 : ++integer);
        }
        System.out.println(map);
    }

    @RequestMapping("create-person")
    public Person createPerson() {
        return personRep.save(new Person());
    }

    @RequestMapping("create-statistics_record")
    public StatisticsRecord createStatisticsRecord(){
        return statisticsRecord.save(new StatisticsRecord());
    }
}
