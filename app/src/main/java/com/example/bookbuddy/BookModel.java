package com.example.bookbuddy;

public class BookModel {
    private String name;
    private String author;
    private String coverImageUrl; // String to store cover image URL
    private String summary;

    public BookModel(String name, String author, String coverImageUrl, String summary) {
        this.name = name;
        this.author = author;
        this.coverImageUrl = coverImageUrl;
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
