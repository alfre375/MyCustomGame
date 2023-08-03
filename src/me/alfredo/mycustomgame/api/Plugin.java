package me.alfredo.mycustomgame.api;

public interface Plugin {
    void onEnable();

    void onDisable();

    String getName();
}
