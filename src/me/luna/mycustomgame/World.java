package me.luna.mycustomgame;

import java.io.File;

public class World {
    String name;
    File folder;
    long worldBorderSize;

    public World(String name, File folder, long worldBorderSize) {
        this.name = name;
        this.folder = folder;
        this.worldBorderSize = worldBorderSize;
    }

    public static World getWorld(String name) {
        //
        return null;
    }
}
