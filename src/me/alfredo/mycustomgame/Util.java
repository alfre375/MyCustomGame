package me.alfredo.mycustomgame;

import org.jetbrains.annotations.Nullable;

public class Util {
    public String mergeStringList(String[] stringList, @Nullable String seperator) {
        if (seperator == null)
            seperator = "";
        String value = "";
        for (int i = 0; i < stringList.length; i++) {
            value = value + stringList[i];
            if (i != stringList.length-1 && seperator != "\\null")
                value = value + seperator;
        }
        return value;
    }
    public String mergeStringList(Object[] stringList, @Nullable String seperator) {
        if (seperator == null)
            seperator = "";
        String value = "";
        for (int i = 0; i < stringList.length; i++) {
            value = value + stringList[i];
            if (i != stringList.length-1 && seperator != "\\null")
                value = value + seperator;
        }
        return value;
    }
}
