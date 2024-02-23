package org.example.tbs.roleplayplugin.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {

    private Connection connection;
    private String host, database, username, password;
    private Logger logger;

    public MySQLConnection(String host, String database, String username, String password, Logger logger) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.logger = logger;
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + host + "/" + database;
            connection = DriverManager.getConnection(url, username, password);
            logger.log(Level.INFO, "Connected to the database!");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to the database.", e);
            return false;
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.log(Level.INFO, "Disconnected from the database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error disconnecting from the database.", e);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query.", e);
            return null;
        }
    }

    public int executeUpdate(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing update.", e);
            return -1;
        }
    }
}
