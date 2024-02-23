package org.example.tbs.roleplayplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.tbs.roleplayplugin.sql.MySQLConnection;
import org.example.tbs.roleplayplugin.sql.MySQLTableCreator;

import java.util.logging.Logger;

public class RoleplayPlugin extends JavaPlugin {

    private MySQLConnection mysqlConnection;
    private MySQLTableCreator tableCreator;

    @Override
    public void onEnable() {
        // Konfigurationsdaten für die Datenbank
        String host = "localhost";
        String database = "rp_datenbank";
        String username = "root";
        String password = "";

        Logger mysqlLogger = Logger.getLogger("DeinMySQLLogger");

        mysqlConnection = new MySQLConnection(host, database, username, password, mysqlLogger);

        if (mysqlConnection.connect()) {
            tableCreator = new MySQLTableCreator(mysqlConnection.getConnection(), mysqlLogger);
            tableCreator.createPlayerRegisterTable();

            // Führe hier Aktionen aus, die bei Plugin-Aktivierung notwendig sind
            getLogger().info("Plugin erfolgreich aktiviert!");
        } else {
            getLogger().severe("Fehler bei der Verbindung zur Datenbank!");
            // Deaktiviere das Plugin, wenn die Verbindung fehlschlägt
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        mysqlConnection.disconnect();
        getLogger().info("Plugin deaktiviert.");
    }
}


