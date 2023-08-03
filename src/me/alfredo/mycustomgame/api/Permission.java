package me.alfredo.mycustomgame.api;

public class Permission {
    String permission;
    boolean includedWithOp;
    public Permission(String permission, boolean includedWithOp) {
        this.permission = permission;
        this.includedWithOp = includedWithOp;
    }
    public Permission(String permission) {
        this.permission = permission;
        this.includedWithOp = false;
    }
    public String getPermission() {
        return permission;
    }
    public boolean isIncludedWithOp(){
        return includedWithOp;
    }
    public Permission clone() {
        return new Permission(permission, includedWithOp);
    }
    public static Permission generatePermission(String permission) {
        return new Permission(permission);
    }
    public static Permission generatePermission(String permission, boolean includedWithOp) {
        return new Permission(permission, includedWithOp);
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public void setIncludedWithOp(boolean includedWithOp) {
        this.includedWithOp = includedWithOp;
    }
    public void copy(Permission permission) {
        this.permission = permission.getPermission();
        this.includedWithOp = permission.isIncludedWithOp();
    }
    public boolean playerHasPerm(Player p) {
        boolean can = p.hasPermission(permission);
        if (includedWithOp && p.isOp()) {
            can = true;
        }
        return can;
    }
}
