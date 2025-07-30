package me.luna.mycustomgame.commands;

import me.luna.mycustomgame.ChatColor;
import me.luna.mycustomgame.Util;
import me.alfredo.mycustomgame.api.*;
import me.luna.mycustomgame.api.CommandSender;
import me.luna.mycustomgame.api.Plugin;
import me.luna.mycustomgame.api.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class PluginsCommand {
    Util util = new Util();
    public void onPluginCommand(CommandSender sender, String command, String[] args) {
        List<String> pluginNames = new ArrayList<>();
        for (Plugin plugin : PluginManager.pluginNameMap.keySet()) {
            boolean enabled = PluginManager.isEnabledStatic(plugin);
            if (enabled) {
                pluginNames.add(ChatColor.GREEN + plugin.getName() + ChatColor.RESET);
            } else {
                pluginNames.add(ChatColor.RED + plugin.getName() + ChatColor.RESET);
            }
        }
        sender.sendMessage(util.mergeStringList(pluginNames.toArray(new String[0]), ChatColor.RESET + " ") + ChatColor.RESET);
    }
}
