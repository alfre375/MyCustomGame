package me.luna.mycustomgame.api;

public interface CommandSender {
    void sendMessage(String message);
    void executeCommand(String label, String[] args);
}
