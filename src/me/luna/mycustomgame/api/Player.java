package me.luna.mycustomgame.api;

import me.luna.mycustomgame.Location;

import java.util.List;
import java.util.UUID;

public interface Player extends CommandSender {
    void teleport(Location location);
    String getName();
    void kick(String reason);
    Location getLocation();
    void addPerm(String perm);
    void addPerm(Permission perm);
    void removePerm(String perm);
    void removePerm(Permission perm);
    List<String> getPerms();
    boolean hasPermission(String perm);
    boolean hasPermission(Permission perm);
    String getDisplayName();
    void setDisplayName(String displayName);
    boolean isOp();
    void setOp(boolean op);
    void setUUID(UUID uuid);
    UUID getUUID();
}
