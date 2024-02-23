package org.example.tbs.roleplayplugin.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.tbs.roleplayplugin.sql.MySQLTableCreator;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private MySQLTableCreator tableCreator;

    public PlayerJoinListener(MySQLTableCreator tableCreator) {
        this.tableCreator = tableCreator;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        UUID playerUUID = event.getPlayer().getUniqueId();

        // Insert the player into the database
        tableCreator.insertPlayer(playerName, playerUUID);
    }
}
