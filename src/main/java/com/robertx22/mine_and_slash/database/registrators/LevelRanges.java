package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;

public class LevelRanges implements ISlashRegistryInit {

    public static LevelRange STARTER = new LevelRange(0, 0.2F, "newbie");
    public static LevelRange LOW = new LevelRange(0.2F, 0.4F, "low");
    public static LevelRange MIDDLE = new LevelRange(0.4F, 0.6F, "middle");
    public static LevelRange HIGH = new LevelRange(0.6F, 0.8F, "high");
    public static LevelRange ENDGAME = new LevelRange(0.8F, 1F, "end");

    @Override
    public void registerAll() {
        STARTER.addToSerializables();
        LOW.addToSerializables();
        MIDDLE.addToSerializables();
        HIGH.addToSerializables();
        ENDGAME.addToSerializables();
    }
}
