package ru.geraskindenis.word_statistics;

import java.util.HashMap;
import java.util.Map;

public class WordStatistics {
    /**
     * @param text - текст в котором слова разделены пробелами
     * @return - HashMap<String, Integer> слово и количество его повторений
     */
    public static Map<String, Integer> getNumberOfRepetitions(String text) {
        String[] strings = text.split("\\s+");
        Map<String, Integer> result = new HashMap<>();
        for (String s : strings) {
            Integer integer = result.get(s);
            result.put(s, (integer == null) ? 1 : ++integer);
        }
        return result;
    }
}
