package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.resources.BaseRegenClass;

public class ManaRegen extends BaseRegenClass {
    public static String GUID = "mana_regen";

    public static ManaRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ManaRegen() {
        this.min_val = 0;
        this.scaling = StatScaling.SCALING;
        this.statGroup = StatGroup.MAIN;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Regen";
    }

    private static class SingletonHolder {
        private static final ManaRegen INSTANCE = new ManaRegen();
    }
}
