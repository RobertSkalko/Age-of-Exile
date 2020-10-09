package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.HealEffectivenessOnSelfEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class HealEffectivenessOnSelf extends Stat implements IStatEffects {
    public static String GUID = "heal_effect_on_self";

    private HealEffectivenessOnSelf() {
        this.add$To$toTooltip = false;
        this.statGroup = StatGroup.RESTORATION;
    }

    public static HealEffectivenessOnSelf getInstance() {
        return HealEffectivenessOnSelf.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects all heal effects applied to you.";
    }

    @Override
    public IStatEffect getEffect() {
        return new HealEffectivenessOnSelfEffect();
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
        return "Heal Effectiveness";
    }

    private static class SingletonHolder {
        private static final HealEffectivenessOnSelf INSTANCE = new HealEffectivenessOnSelf();
    }
}


