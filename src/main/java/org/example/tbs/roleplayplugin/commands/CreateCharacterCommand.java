package org.example.tbs.roleplayplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.tbs.roleplayplugin.sql.MySQLTableCreator;

import java.util.UUID;

public class CreateCharacterCommand implements CommandExecutor {

    private MySQLTableCreator tableCreator;

    public CreateCharacterCommand(MySQLTableCreator tableCreator) {
        this.tableCreator = tableCreator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Usage: /createcharacter <character_name>");
            return true;
        }

        String characterName = args[0];

        // Hier kannst du zusätzliche Überprüfungen für den Charakternamen durchführen, falls gewünscht

        // Rufe die Methode auf, um die Spieler-ID zu erhalten
        int playerId = getPlayerId(player.getUniqueId());

        // Rufe die Methode auf, um den Charakter in die Datenbank einzufügen
        tableCreator.insertCharacter(playerId, characterName);

        player.sendMessage("Character created successfully!");

        return true;
    }

    private int getPlayerId(UUID playerUUID) {

        return tableCreator.getPlayerIdForUUID(playerUUID);

        // Hier müsstest du den tatsächlichen Code hinzufügen, um die Spieler-ID zu erhalten
    }
}

