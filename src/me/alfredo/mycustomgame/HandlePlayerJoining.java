package me.alfredo.mycustomgame;

import me.alfredo.mycustomgame.api.Permission;
import me.alfredo.mycustomgame.api.Player;
import me.alfredo.mycustomgame.api.PluginManager;
import me.alfredo.mycustomgame.api.events.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HandlePlayerJoining {
    public static HashMap<Player, String> playerNameMap = new HashMap<>();
    public static HashMap<Player, String> playerDisplayNameMap = new HashMap<>();
    public static HashMap<Player, Location> playerLocationMap = new HashMap<>();
    public static HashMap<Player, List<String>> playerPermissionMap = new HashMap<>();
    public static HashMap<Player, Boolean> playerOpMap = new HashMap<>();
    public static HashMap<Player, UUID> playerUUIDMap = new HashMap<>();
    public Object c;

    private Player p = new Player() {
        private Location location;

        @Override
        public void teleport(Location loc) {
            this.location = loc;
        }

        @Override
        public String getName() {
            return playerNameMap.get(this);
        }

        @Override
        public void kick(String reason) {
            playerNameMap.remove(this);
            playerLocationMap.remove(this);
            playerDisplayNameMap.remove(this);
            playerPermissionMap.remove(this);
        }

        @Override
        public Location getLocation() {
            return playerLocationMap.get(this);
        }

        @Override
        public void addPerm(String perm) {
            List<String> perms = playerPermissionMap.get(this);
            perms.add(perm);
            playerPermissionMap.replace(this, perms);
        }

        @Override
        public void addPerm(Permission perm) {
            addPerm(perm.getPermission());
        }

        @Override
        public void removePerm(String perm) {
            List<String> perms = playerPermissionMap.get(this);
            perms.remove(perm);
            playerPermissionMap.replace(this, perms);
        }

        @Override
        public void removePerm(Permission perm) {
            removePerm(perm.getPermission());
        }

        @Override
        public List<String> getPerms() {
            return playerPermissionMap.get(this);
        }

        @Override
        public boolean hasPermission(String perm) {
            return playerPermissionMap.containsKey(perm);
        }

        @Override
        public boolean hasPermission(Permission perm) {
            return hasPermission(perm.getPermission());
        }

        @Override
        public String getDisplayName() {
            return playerDisplayNameMap.get(this);
        }

        @Override
        public void setDisplayName(String displayName) {
            if (playerDisplayNameMap.containsKey(this)) {
                playerDisplayNameMap.replace(this, displayName);
            } else {
                playerDisplayNameMap.put(this, displayName);
            }
        }

        @Override
        public boolean isOp() {
            if (!playerOpMap.containsKey(this))
                return false;
            return playerOpMap.get(this);
        }

        @Override
        public void setOp(boolean op) {
            playerOpMap.replace(this, op);
        }

        @Override
        public void setUUID(UUID uuid) {
            playerUUIDMap.replace(this, uuid);
        }

        @Override
        public UUID getUUID() {
            return playerUUIDMap.get(this);
        }

        @Override
        public void sendMessage(String message) {
            //
        }

        @Override
        public void executeCommand(String label, String[] args) {
            //
        }
    };
    PluginManager pm = new PluginManager();
    PlayerJoinEvent pje = new PlayerJoinEvent(p, ChatColor.YELLOW + p.getDisplayName() + " joined" +
            ChatColor.RESET);
    public void establishPJE() {
        pje = new PlayerJoinEvent(p, ChatColor.YELLOW + p.getDisplayName() + " joined" + ChatColor.RESET);
    }

    public HandlePlayerJoining(Object c, String name, String displayName, UUID uuid) {
        playerNameMap.put(p, name);
        p.setDisplayName(displayName);
        p.setUUID(uuid);
        establishPJE();
        pm.handleEvents(pje);
    }

    public HandlePlayerJoining(Object c, String name, UUID uuid) {
        playerNameMap.put(p, name);
        p.setDisplayName(name);
        p.setUUID(uuid);
        establishPJE();
        pm.handleEvents(pje);
    }

    public HandlePlayerJoining(Object c, String name, String displayName) {
        playerNameMap.put(p, name);
        p.setDisplayName(displayName);
        p.setUUID(UUID.randomUUID());
        establishPJE();
        pm.handleEvents(pje);
    }

    public HandlePlayerJoining(Object c, String name) {
        playerNameMap.put(p, name);
        p.setDisplayName(name);
        p.setUUID(UUID.randomUUID());
        establishPJE();
        pm.handleEvents(pje);
    }

    public Player getPlayer() {
        return p;
    }
    public void setPlayer(Player p) {
        this.p = p;
    }
}
