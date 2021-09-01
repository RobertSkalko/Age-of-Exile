package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;

import java.util.Arrays;
import java.util.List;

public class LevelRanges {

    public static LevelRange STARTER = new LevelRange(0 + "", 0, 0.2F);
    public static LevelRange LOW = new LevelRange(1 + "", 0.2F, 0.4F);
    public static LevelRange MIDDLE = new LevelRange(2 + "", 0.4F, 0.6F);
    public static LevelRange HIGH = new LevelRange(3 + "", 0.6F, 0.8F);
    public static LevelRange ENDGAME = new LevelRange(4 + "", 0.8F, 1F);

    public static LevelRange fromTier(int tier) {

        if (tier == 0) {
            return STARTER;
        }
        if (tier == 1) {
            return LOW;
        }
        if (tier == 2) {
            return MIDDLE;
        }
        if (tier == 3) {
            return HIGH;
        }
        if (tier == 4) {
            return ENDGAME;
        }

        return STARTER;
    }

    public static LevelRange START_TO_LOW = new LevelRange("_low", 0F, 0.4F);
    public static LevelRange MID_TO_END = new LevelRange("_end", 0.4F, 1F);
    public static LevelRange START_TO_END = new LevelRange("_full", 0, 1F);

    public static List<LevelRange> allNormal() {
        return Arrays.asList(STARTER, LOW, MIDDLE, HIGH, ENDGAME);
    }

    public static List<LevelRange> allJewelry() {
        return Arrays.asList(START_TO_LOW, MID_TO_END);
    }
}
