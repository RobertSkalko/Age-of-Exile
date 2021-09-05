package com.robertx22.age_of_exile.database.data.stats.types.resources.energy;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.resources.BaseRegenClass;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;

public class EnergyRegen extends BaseRegenClass {
    public static String GUID = "energy_regen";

    public static EnergyRegen getInstance() {
        return EnergyRegen.SingletonHolder.INSTANCE;
    }

    private EnergyRegen() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.energy;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Energy Regen";
    }

    private static class SingletonHolder {
        private static final EnergyRegen INSTANCE = new EnergyRegen();
    }
}

