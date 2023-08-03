package me.alfredo.mycustomgame.api.events;

import me.alfredo.mycustomgame.api.Player;

public class PlayerLeaveEvent implements Event {
    Player player;
    String quitMessage;
    public PlayerLeaveEvent(Player p, String quitMessage) {
        this.player = p;
        this.quitMessage = quitMessage;
    }

    public Player getPlayer() {
        return player;
    }

    public String getQuitMessage() {
        return quitMessage;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    @Override
    public EventType getEventType() {
        return EventType.PLAYER_LEAVE;
    }


    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancelled) {}
}
