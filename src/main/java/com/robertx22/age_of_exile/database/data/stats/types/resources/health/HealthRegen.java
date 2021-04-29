package com.robertx22.age_of_exile.database.data.stats.types.resources.health;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.resources.BaseRegenClass;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class HealthRegen extends BaseRegenClass {
    public static String GUID = "health_regen";

    public static HealthRegen getInstance() {
        return SingletonHolder.INSTANCE;

    }

    private HealthRegen() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.MAIN;
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
    public ResourceType getResourceType() {
        return ResourceType.HEALTH;
    }

    @Override
    public String locNameForLangFile() {
        return "Health Regen";
    }

    private static class SingletonHolder {
        private static final HealthRegen INSTANCE = new HealthRegen();
    }
}
