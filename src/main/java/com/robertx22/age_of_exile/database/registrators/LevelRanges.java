package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;

public class LevelRanges {

    public static LevelRange STARTER = new LevelRange(0, 0.2F);
    public static LevelRange LOW = new LevelRange(0.2F, 0.4F);
    public static LevelRange MIDDLE = new LevelRange(0.4F, 0.6F);
    public static LevelRange HIGH = new LevelRange(0.6F, 0.8F);
    public static LevelRange ENDGAME = new LevelRange(0.8F, 1F);

    public static LevelRange START_TO_LOW = new LevelRange(0F, 0.4F);
    public static LevelRange MID_TO_END = new LevelRange(0.4F, 1F);

}
