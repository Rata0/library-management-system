package org.example.manager;

import java.sql.Connection;

public interface DatabaseManagerInterface {
    void connect();
    void disconnect();
    Connection getConnect();
}
