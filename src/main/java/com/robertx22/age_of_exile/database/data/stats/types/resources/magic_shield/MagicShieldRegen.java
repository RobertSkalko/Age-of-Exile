package com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.resources.BaseRegenClass;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class MagicShieldRegen extends BaseRegenClass {

    public static String GUID = "magic_shield_regen";

    private MagicShieldRegen() {
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.MAIN;
    }

    public static MagicShieldRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield Regen";
    }

    private static class SingletonHolder {
        private static final MagicShieldRegen INSTANCE = new MagicShieldRegen();
    }
}
