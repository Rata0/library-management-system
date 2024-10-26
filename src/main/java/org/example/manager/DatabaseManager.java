package org.example;

import org.example.model.Book;
import org.example.model.Reader;

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setDate(3, new java.sql.Date(publishedDate.getTime()));
            preparedStatement.setString(4, isbn);
            preparedStatement.executeUpdate();
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Найдена книга по названию " + title);
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("published_date"),
                        rs.getString("isbn")
                );
            } else {
                System.out.println("Книга не найдена по названию " + title);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске книги: " + e.getMessage());
        }
        return null;
    }

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

    public void addReader(String name, String email) {
        String sql = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно добавлен");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при добавление пользователя " + e.getMessage());
        }
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reader reader = new Reader(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                readers.add(reader);
            }
            System.out.println("Получено читателей: " + readers.size());
        } catch (SQLException e) {
            System.out.println("Ошибка при получение читателей " + e.getMessage());
        }
        return readers;
    }

    public Reader findReaderByEmail(String email) {
        String sql = "SELECT * FROM readers WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Найден пользователь с почтой " + email);
                return new Reader(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            } else {
                System.out.println("Не найден пользователь с почтой " + email);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при поиске читателя с почтой " + email + " " + e.getMessage());
        }
        return null;
    }

    public void deleteReader(int id) {
        String sql = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();

            if (count > 0) {
                System.out.println("Читатель с ID " + id + " успешно удален");
            } else {
                System.out.println("Читатель с ID " + id + " не найден");
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при удалении читателя с ID " + id + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();

        databaseManager.deleteBook(1);

        databaseManager.deleteReader(2);

        databaseManager.disconnect();
    }
}