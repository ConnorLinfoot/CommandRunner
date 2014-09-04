package com.connorlinfoot.commandrunner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class Main extends JavaPlugin {

    public void onEnable() {
        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();
        console.sendMessage(ChatColor.GREEN + "========== CommandRunner! ==========");
        console.sendMessage(ChatColor.GREEN + "=========== VERSION: 1.0 ===========");
        console.sendMessage(ChatColor.GREEN + "======== BY CONNOR LINFOOT! ========");
    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command commandd, String label, String[] args) {
        if( sender instanceof Player ){
            Player p = (Player) sender;
            if( !p.hasPermission("commandrunner.use") ){
                sender.sendMessage("You dont have the \"commandrunner.use\" permission");
                return false;
            }
        }

        if( args.length >= 2 ){
            StringBuilder builder = new StringBuilder();
            for (String value : args) {
                builder.append(value).append(" ");
            }
            String command = builder.toString();
            command = command.replace(args[0] + " ","");

            if( args[0].equalsIgnoreCase("console") ){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
                sender.sendMessage("Your command was ran from console");
                return true;
            }

            Player runAs = Bukkit.getPlayer(args[0]);
            if( runAs == null ){
                sender.sendMessage("Player not found");
                return false;
            }

            runAs.performCommand(command);
            sender.sendMessage("Your command was ran from the player " + runAs.getName());
            return true;

        }

        sender.sendMessage(ChatColor.RED + "Usage: /run <player/console> <command>");

        return true;
    }
}
