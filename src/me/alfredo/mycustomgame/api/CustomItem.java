package me.alfredo.mycustomgame.api;

import java.util.UUID;

public interface CustomItem {
    String getImageMapPath();
    String getKey();
    String getLocationInLangFile();
    void setLocationInLangFile();
    void onRightClick();
    void onLeftClick();
    int damageGiven();
    int knockBackGiven();
    double protection(int damageDealt);
    int protectionLevel();
    WearSlot[] getSlotWearable(); // Can return null if none. Usually will return null or 1, but can return multiple
    UUID getItemUniqueIdentifier();
}
