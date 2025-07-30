package me.luna.mycustomgame.api;

public interface Plugin {
    void onEnable();

    void onDisable();

    String getName();
}
