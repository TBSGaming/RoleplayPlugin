package org.example.tbs.roleplayplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.example.tbs.roleplayplugin.listeners.PlayerJoinListener;
import org.example.tbs.roleplayplugin.sql.MySQLConnection;
import org.example.tbs.roleplayplugin.sql.MySQLTableCreator;

import java.util.logging.Logger;

public class RoleplayPlugin extends JavaPlugin {

    private MySQLConnection mysqlConnection;
    private MySQLTableCreator tableCreator;

    @Override
    public void onEnable() {
        // Database configuration
        String host = "localhost";
        String database = "rp_datenbank";
        String username = "root";
        String password = "";

        Logger mysqlLogger = Logger.getLogger("YourMySQLLogger");

        mysqlConnection = new MySQLConnection(host, database, username, password, mysqlLogger);

        if (mysqlConnection.connect()) {
            tableCreator = new MySQLTableCreator(mysqlConnection.getConnection(), mysqlLogger);
            tableCreator.createPlayerRegisterTable();

            // Register the PlayerJoinListener
            PluginManager pluginManager = getServer().getPluginManager();
            pluginManager.registerEvents(new PlayerJoinListener(tableCreator), this);

            getLogger().info("Plugin successfully enabled!");
        } else {
            getLogger().severe("Error connecting to the database!");
            // Disable the plugin if the connection fails
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        mysqlConnection.disconnect();
        getLogger().info("Plugin disabled.");
    }
}


