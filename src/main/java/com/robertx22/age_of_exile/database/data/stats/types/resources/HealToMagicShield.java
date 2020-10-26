package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.HealGoesToMagicShieldEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class HealToMagicShield extends Stat implements IStatEffects {
    public static String GUID = "heal_to_ms";

    private HealToMagicShield() {
        this.add$To$toTooltip = false;
        this.statGroup = StatGroup.RESTORATION;
    }

    public static HealToMagicShield getInstance() {
        return HealToMagicShield.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects all heal effects applied to you.";
    }

    @Override
    public IStatEffect getEffect() {
        return HealGoesToMagicShieldEffect.getInstance();
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
        return "of Heal effects to Magic Shield";
    }

    private static class SingletonHolder {
        private static final HealToMagicShield INSTANCE = new HealToMagicShield();
    }
}
