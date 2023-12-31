package me.alfredo.mycustomgame.api.events;

import me.alfredo.mycustomgame.api.Player;

public class PlayerJoinEvent implements Event {
    Player player;
    String joinMessage;
    boolean cancelled;
    public PlayerJoinEvent(Player p, String joinMessage) {
        this.player = p;
        this.joinMessage = joinMessage;
    }

    public Player getPlayer() {
        return player;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    @Override
    public EventType getEventType() {
        return EventType.PLAYER_JOIN;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
