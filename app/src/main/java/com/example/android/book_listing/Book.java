package com.example.android.book_listing;

/**
 * Created by user on 7/17/2017.
 */
public class Book {
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private String description;


    public Book(String title, String publisher, String publishedDate, String description, String author) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

}
