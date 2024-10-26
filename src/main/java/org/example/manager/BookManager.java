package org.example.manager;

import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookManager  implements BookManagerInterface {
    private final Connection connection;

    public BookManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBook(String title, String author, Date publishedDate, String isbn) {
        String sql = "INSERT INTO books (title, author, published_date, isbn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setDate(3, new java.sql.Date(publishedDate.getTime()));
            preparedStatement.setString(4, isbn);
            preparedStatement.executeUpdate();
            System.out.println("Книга успешно добавлена: " + title);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("published_date"),
                        rs.getString("isbn")
                );
                books.add(book);
            }
            System.out.println("Получено книг: " + books.size());
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка книг: " + e.getMessage());
        }
        return books;
    }

    @Override
    public Book findBookByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Найдена книга по названию: " + title);
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("published_date"),
                        rs.getString("isbn")
                );
            } else {
                System.out.println("Книга не найдена по названию: " + title);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске книги: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();

            if (count > 0) {
                System.out.println("Книга с ID " + id + " успешно удалена");
            } else {
                System.out.println("Книга с ID " + id + " не найдена");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удаление книги: " + e.getMessage());
        }
    }
}
