package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;

public class ManaRegen extends BaseRegenClass {
    public static String GUID = "mana_regen";

    public static ManaRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    @Override
    public String getIconPath() {
        return "regen/mana_regen";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
    }

    private ManaRegen() {
        this.min_val = 0;
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
