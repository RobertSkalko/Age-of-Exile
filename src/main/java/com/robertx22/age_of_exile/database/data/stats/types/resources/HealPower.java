package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.IncreaseHealingEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class HealPower extends Stat implements IStatEffects {
    public static String GUID = "increase_healing";

    private HealPower() {
        this.add$To$toTooltip = false;
    }

    public static HealPower getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Regeneration;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases all types of healing recieved like health regen, lifesteal, life on hit, spell heals etc";
    }

    @Override
    public IStatEffect getEffect() {
        return new IncreaseHealingEffect();
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Healing Power";
    }

    private static class SingletonHolder {
        private static final HealPower INSTANCE = new HealPower();
    }
}

