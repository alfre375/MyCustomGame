package me.alfredo.mycustomgame.api.events;

import me.alfredo.mycustomgame.api.CommandSender;

public class PlayerChatEvent implements Event {

    String messageSent;

    boolean wasCommand;
    CommandSender sender;
    boolean cancelled = false;
    public PlayerChatEvent(String messageSent, boolean wasCommand, CommandSender sender) {
        this.messageSent = messageSent;
        this.wasCommand = wasCommand;
        this.sender = sender;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public void setMessage(String messageSent) {
        this.messageSent = messageSent;
    }

    public boolean wasCommand() {
        return wasCommand;
    }

    public void setWasCommand(boolean wasCommand) {
        this.wasCommand = wasCommand;
    }

    public CommandSender getSender() {
        return sender;
    }

    @Override
    public EventType getEventType() {
        return EventType.PLAYER_CHAT;
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
