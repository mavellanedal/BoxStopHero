package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB = "jdbc:mysql://localhost:3306/box_stop_hero";
    private static final String USERNAME = "databaseManagerJava";
    private static final String PASSWORD = "Temporal01";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB, USERNAME, PASSWORD);
    }
}