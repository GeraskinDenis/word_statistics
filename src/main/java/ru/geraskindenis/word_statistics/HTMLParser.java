package ru.geraskindenis.word_statistics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLParser {
    public static String getText(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return document.text();
    }
}
