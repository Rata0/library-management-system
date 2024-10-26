package org.example.manager;

import java.sql.*;

public class DatabaseManager implements DatabaseManagerInterface {
    private static final String URL = "jdbc:postgresql://localhost/library_db";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Успешное подкючение к базе");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при подключение к базе");
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
            System.out.println("Соединение с базой данных успешно закрыто");
        } catch (SQLException e) {
            System.err.println("Произошла ошибка при закрытии соединения с базой данных");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnect() {
        return connection;
    }
}
