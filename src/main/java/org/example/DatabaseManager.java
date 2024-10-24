package org.example;

import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";
    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Успешное подкючение к базе");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при подключение к базе");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Соединение с базой данных успешно закрыто");
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при закрытии соединения с базой данных");
            e.printStackTrace();
        }
    }

    public void addBook(String title, String author, Date publishedDate, String isbn) {
        String sql = "INSERT INTO books (title, author, published_date, isbn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setDate(3, new java.sql.Date(publishedDate.getTime()));
            pstmt.setString(4, isbn);
            pstmt.executeUpdate();
            System.out.println("Книга успешно добавлена " + title);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении книги " + e.getMessage());
        }
    }

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
            System.out.println("Получено книг " + books.size());
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка книг " + e.getMessage());
        }
        return books;
    }

    public Book findBookByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Найдена книга по названию " + title);
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("published_date"),
                        rs.getString("isbn"));
            } else {
                System.out.println("Книга не найдена по названию");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске книги: " + e.getMessage());
        }
        return null;
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int count = pstmt.executeUpdate();

            if (count > 0) {
                System.out.println("Книга с ID " + id + " успешно удалена");
            } else {
                System.out.println("Книга с ID " + id + " не найдена");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удаление книги: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();

        databaseManager.deleteBook(2);
        databaseManager.disconnect();
    }
}
