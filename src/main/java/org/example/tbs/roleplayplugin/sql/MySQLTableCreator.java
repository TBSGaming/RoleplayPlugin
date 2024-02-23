package org.example.tbs.roleplayplugin.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            logger.log(Level.INFO, "Tabelle 'player_register' erfolgreich erstellt.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Erstellen der Tabelle 'player_register'", e);
        }
    }

    public void insertPlayer(String name, UUID uuid) {
        String insertQuery = "INSERT INTO player_register (name, uuid) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            logger.log(Level.INFO, "Spieler erfolgreich in 'player_register' eingefügt.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Einfügen des Spielers in 'player_register'", e);
        }
    }
}