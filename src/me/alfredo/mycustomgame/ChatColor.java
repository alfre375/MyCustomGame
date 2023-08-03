package me.alfredo.mycustomgame;

import org.jetbrains.annotations.Nullable;

public class ChatColor {
    public static final String RESET = ChatColors.RESET.getCode();
    public static final String BLACK = ChatColors.BLACK.getCode();
    public static final String RED = ChatColors.RED.getCode();
    public static final String GREEN = ChatColors.GREEN.getCode();
    public static final String YELLOW = ChatColors.YELLOW.getCode();
    public static final String BLUE = ChatColors.BLUE.getCode();
    public static final String MAGENTA = ChatColors.MAGENTA.getCode();
    public static final String CYAN = ChatColors.CYAN.getCode();
    public static final String WHITE = ChatColors.WHITE.getCode();
    public static void print(String msg, String translateAltColorCodesString) {
        if (translateAltColorCodesString.toCharArray().length != 1) {
            System.out.print(msg + ChatColors.getDefaultColorCode());
            return;
        }
        msg = msg.replaceAll(translateAltColorCodesString + "r", RESET);
        msg = msg.replaceAll(translateAltColorCodesString + "0", BLACK);
        msg = msg.replaceAll(translateAltColorCodesString + "c", RED);
        msg = msg.replaceAll(translateAltColorCodesString + "a", GREEN);
        msg = msg.replaceAll(translateAltColorCodesString + "e", YELLOW);
        msg = msg.replaceAll(translateAltColorCodesString + "9", BLUE);
        msg = msg.replaceAll(translateAltColorCodesString + "g", MAGENTA);
        msg = msg.replaceAll(translateAltColorCodesString + "b", CYAN);
        msg = msg.replaceAll(translateAltColorCodesString + "f", WHITE);
        msg = msg + RESET;
        System.out.print(msg);
    }
    public static void println(String msg, String translateAltColorCodesString) {
        if (translateAltColorCodesString.toCharArray().length != 1) {
            System.out.println(msg + ChatColors.getDefaultColorCode());
            return;
        }
        msg = msg.replaceAll(translateAltColorCodesString + "r", RESET);
        msg = msg.replaceAll(translateAltColorCodesString + "0", BLACK);
        msg = msg.replaceAll(translateAltColorCodesString + "c", RED);
        msg = msg.replaceAll(translateAltColorCodesString + "a", GREEN);
        msg = msg.replaceAll(translateAltColorCodesString + "e", YELLOW);
        msg = msg.replaceAll(translateAltColorCodesString + "9", BLUE);
        msg = msg.replaceAll(translateAltColorCodesString + "g", MAGENTA);
        msg = msg.replaceAll(translateAltColorCodesString + "b", CYAN);
        msg = msg.replaceAll(translateAltColorCodesString + "f", WHITE);
        msg = msg + RESET;
        System.out.println(msg);
    }
    public static String translateAlternateColorCodes(String msg, char translateAltColorCodeChar, @Nullable boolean noReset) {
        msg = msg.replaceAll(translateAltColorCodeChar + "r", RESET);
        msg = msg.replaceAll(translateAltColorCodeChar + "0", BLACK);
        msg = msg.replaceAll(translateAltColorCodeChar + "c", RED);
        msg = msg.replaceAll(translateAltColorCodeChar + "a", GREEN);
        msg = msg.replaceAll(translateAltColorCodeChar + "e", YELLOW);
        msg = msg.replaceAll(translateAltColorCodeChar + "9", BLUE);
        msg = msg.replaceAll(translateAltColorCodeChar + "g", MAGENTA);
        msg = msg.replaceAll(translateAltColorCodeChar + "b", CYAN);
        msg = msg.replaceAll(translateAltColorCodeChar + "f", WHITE);
        if (noReset) {
            return msg;
        }
        return RESET + msg + RESET;
    }
}
