package org.example.tbs.roleplayplugin.sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLTableCreator {

    private Connection connection;
    private Logger logger;

    public MySQLTableCreator(Connection connection, Logger logger) {
        this.connection = connection;
        this.logger = logger;
    }

    public void createPlayerRegisterTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS player_register ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "uuid VARCHAR(36) NOT NULL UNIQUE"
                + ");";

        try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.executeUpdate();
            logger.log(Level.INFO, "Table 'player_register' created successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating table 'player_register'", e);
        }
    }
    public boolean isPlayerRegistered(UUID uuid) {
        String selectQuery = "SELECT id FROM player_register WHERE uuid = ?;";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Gibt true zurück, wenn der Spieler gefunden wurde, sonst false
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking player registration in 'player_register'", e);
            return false; // Bei einem Fehler wird davon ausgegangen, dass der Spieler nicht registriert ist
        }
    }

    public void insertPlayer(String name, UUID uuid) {
        if (isPlayerRegistered(uuid)) {
            logger.log(Level.INFO, "Player is already registered. Player: " + name + ", UUID: " + uuid);
            return; // Spieler ist bereits registriert, daher wird der Einfügevorgang übersprungen
        }

        String insertQuery = "INSERT INTO player_register (name, uuid) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            logger.log(Level.INFO, "Player successfully inserted into 'player_register'. Player: " + name + ", UUID: " + uuid);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting player into 'player_register'", e);
        }
    }
}

