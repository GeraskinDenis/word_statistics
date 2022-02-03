package ru.geraskindenis.word_statistics.db;

import javax.persistence.*;

@Entity
@Table(name = "word_statistics")
public class StatisticsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "word")
    private String word;

    @Column(name = "quantity")
    private Integer quantity;

    public StatisticsRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
