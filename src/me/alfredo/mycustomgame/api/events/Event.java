package me.alfredo.mycustomgame.api.events;

public interface Event {
    EventType getEventType();
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
