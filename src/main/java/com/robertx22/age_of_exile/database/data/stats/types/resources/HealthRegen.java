package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class HealthRegen extends BaseRegenClass {
    public static String GUID = "health_regen";

    public static HealthRegen getInstance() {
        return SingletonHolder.INSTANCE;

    }

    @Override
    public String getIconPath() {
        return "regen/hp_regen";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
    }

    private HealthRegen() {
        this.min_val = 0.05F;
        this.scaling = StatScaling.SCALING;
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
        return "Health Regeneration";
    }

    private static class SingletonHolder {
        private static final HealthRegen INSTANCE = new HealthRegen();
    }
}
