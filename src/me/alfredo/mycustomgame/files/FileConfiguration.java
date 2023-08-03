package me.alfredo.mycustomgame.files;

import me.alfredo.mycustomgame.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileConfiguration {
    Main main;
    String name = "config.yml";
    public FileConfiguration(Main main, String name) {
        this.main = main;
        this.name = name;
    }
    public FileConfiguration(Main main) {
        this.main = main;
    }
    public File getFile() {
        //
        return null;
    }
    public boolean contains(String path) {
        //
        return false;
    }
    public boolean getBoolean(String path) {
        //
        return false;
    }
    public double getDouble(String path) {
        //
        return 0.0;
    }
    public long getLong(String path) {
        //
        return 0;
    }
    public int getInt(String path) {
        //
        return 0;
    }
    public short getShort(String path) {
        //
        return 0;
    }
    public UUID getUUID(String path) {
        //
        return UUID.randomUUID();
    }
    public String getString(String path) {
        //
        return "";
    }
    public void set(String path, Object value) {
        //
    }
    public ChatColors getColor(String path) {
        //
        return ChatColors.RESET;
    }
    public List<Object> getList(String path) {
        List<Object> list = new ArrayList<>();
        return list;
    }
    public static FileConfiguration getInstance(Main main) {
        return new FileConfiguration(main);
    }
    public Location getLocation(String path) {
        String[] locationPortions = getString(path).split(" ");
        World world = new World("thisIsAWorld",new File(""),0);
        return new Location(0,0,0,0,0, world);
    }
}
