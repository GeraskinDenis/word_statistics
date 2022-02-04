package ru.geraskindenis.word_statistics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLParser {
    public static String getText(String url) throws IOException {
        Document document;
        document = Jsoup.connect(url).get();
        return document.text();
    }
}
