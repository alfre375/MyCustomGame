package me.luna.mycustomgame.api;

public interface CommandExecutor {
    boolean onPluginCommand(CommandSender sender, String command, String[] args);
}