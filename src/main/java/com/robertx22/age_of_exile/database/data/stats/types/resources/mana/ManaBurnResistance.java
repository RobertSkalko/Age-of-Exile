package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ManaBurnResistanceEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ManaBurnResistance extends Stat implements IStatEffects {
    public static String GUID = "mana_burn_resist";

    private ManaBurnResistance() {
        this.scaling = StatScaling.SLOW_SCALING;
    }

    public static ManaBurnResistance getInstance() {
        return ManaBurnResistance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Protects against mana burn by x percent";
    }

    @Override
    public IStatEffect getEffect() {
        return new ManaBurnResistanceEffect();
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
        return "Mana Burn Resist";
    }

    private static class SingletonHolder {
        private static final ManaBurnResistance INSTANCE = new ManaBurnResistance();
    }
}

