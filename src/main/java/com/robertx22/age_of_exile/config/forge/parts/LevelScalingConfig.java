package com.robertx22.age_of_exile.config.forge.parts;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelScalingConfig {

    @ConfigEntry.Gui.CollapsibleObject
    List<LevelScalingRangePart> LEVEL_AND_STAT_GROWTH = new ArrayList<>();

    @ConfigEntry.Gui.Excluded
    transient private HashMap<Integer, Float> cachedMultipliers = new HashMap<>();

    public LevelScalingConfig(List<LevelScalingRangePart> LEVEL_AND_STAT_GROWTH) {
        this.LEVEL_AND_STAT_GROWTH = LEVEL_AND_STAT_GROWTH;
    }

    public LevelScalingConfig() {
    }

    public float getMultiFor(int lvl) {
        // todo this is probably a bad idea for those crazy people that set infinite level caps.
        if (!cachedMultipliers.containsKey(lvl)) {
            for (LevelScalingRangePart x : LEVEL_AND_STAT_GROWTH) {
                if (x.isForLevel(lvl)) {
                    cachedMultipliers.put(lvl, x.getGrowthForLevel(lvl));
                }
            }
            if (!cachedMultipliers.containsKey(lvl)) {
                MMORPG.logError("Stat level growth wasn't setup correctly, no level range for lvl: " + lvl);
                System.out.print("Don't mess with these configs unless you understand what you're doing.");
                return 1;
            }
        }
        return cachedMultipliers.get(lvl);
    }

}
