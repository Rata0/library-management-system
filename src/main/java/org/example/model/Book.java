package org.example.model;

import java.sql.Date;

public class Book {
    private int id;
    private String title;
    private String author;
    private Date publishedDate;
    private String isbn;

    public Book(int id, String title, String author, Date publishedDate, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
