package com.example.bookbuddy;

public class BookModel {

    private String title;
    private String author;
    private String description;
    private int imageResId;

    public BookModel(String title, String author, String description, int imageResId) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
