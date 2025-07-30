package me.luna.mycustomgame;

public class Location {
    public long x;
    public long y;
    public long z;
    public long pitch;
    public long yaw;
    public World world;

    public Location(long x, long y, long z, long pitch, long yaw, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.world = world;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public long getPitch() {return pitch;}

    public long getYaw() {return yaw;}

    public World getWorld() {return world;}

    public Object[] getAll() {
        return new Object[]{x, y, z, pitch, yaw, world};
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public void setPitch(long pitch) {this.pitch = pitch;}

    public void setYaw(long yaw) {this.yaw = yaw;}

    public void setWorld(World world) {this.world = world;}

    // Additional methods and functionality can be added as needed
}
