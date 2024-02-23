package org.example.tbs.roleplayplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.tbs.roleplayplugin.sql.MySQLConnection;

import java.util.logging.Logger;

public class RoleplayPlugin extends JavaPlugin {

    private MySQLConnection mysqlConnection;

    @Override
    public void onEnable() {
        // Konfigurationsdaten für die Datenbank
        String host = "localhsot";
        String database = "rp_datenbank";
        String username = "root";
        String password = "";

        Logger mysqlLogger = Logger.getLogger("DeinMySQLLogger");

        mysqlConnection = new MySQLConnection(host, database, username, password, mysqlLogger);

        if (mysqlConnection.connect()) {
            // Hier kannst du Datenbankabfragen oder andere Aktionen durchführen
        } else {
            getLogger().severe("Fehler bei der Verbindung zur Datenbank!");
        }
    }

    @Override
    public void onDisable() {
        mysqlConnection.disconnect();
    }
}

