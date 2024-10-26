package org.example.manager;

import org.example.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderManager implements ReaderManagerInterface {
    private final Connection connection;

    public ReaderManager(Connection connection) {
        this.connection = connection;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
}
