package me.luna.mycustomgame.api;

import me.luna.mycustomgame.api.events.PlayerJoinEvent;
import me.luna.mycustomgame.api.events.PlayerLeaveEvent;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Player> onlinePlayers = new ArrayList<>();
    private ConsoleSender consoleSender;
    public Server(ConsoleSender consoleSender) {
        this.consoleSender = consoleSender;
    }
    public List<Player> getOnlinePlayers() {
        return onlinePlayers;
    }
    public void setOnlinePlayers(List<Player> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }
    public void playerConnected(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        onlinePlayers.add(p);
    }
    public void playerDisconnected(PlayerLeaveEvent e) {
        Player p = e.getPlayer();
        onlinePlayers.remove(p);
    }

    public ConsoleSender getConsoleSender() {
        return consoleSender;
    }
    public void setConsoleSender(ConsoleSender sender) {
        this.consoleSender = sender;
    }
    public static Server generateInstance (ConsoleSender sender) {
        return new Server(sender);
    }
    public static Server clone(Server server) {
        Server server1 = new Server(server.getConsoleSender());
        server1.setOnlinePlayers(server.getOnlinePlayers());
        return server1;
    }
    public Server clone() {
        return clone(this);
    }
    public void copy(Server server) {
        this.consoleSender = consoleSender;
        this.onlinePlayers = getOnlinePlayers();
    }
}
