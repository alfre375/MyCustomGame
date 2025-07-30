package me.luna.mycustomgame;

public enum ChatColors {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String code;

    ChatColors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static String getDefaultColorCode() {
        return RESET.getCode();
    }
}
