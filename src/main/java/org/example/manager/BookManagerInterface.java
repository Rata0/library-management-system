package org.example.manager;

import org.example.model.Book;

import java.util.Date;
import java.util.List;

public interface BookManagerInterface {
    void addBook(String title, String author, Date publishedDate, String isbn);
    List<Book> getAllBooks();
    Book findBookByTitle(String title);
    void deleteBook(int id);
}
