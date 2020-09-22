package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ManaBurnEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ManaBurn extends Stat implements IStatEffects {
    public static String GUID = "mana_burn";

    private ManaBurn() {
        this.scaling = StatScaling.SCALING;
    }

    public static ManaBurn getInstance() {
        return ManaBurn.SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Misc;
    }

    @Override
    public String locDescForLangFile() {
        return "Burns mana on basic attack";
    }

    @Override
    public IStatEffect getEffect() {
        return new ManaBurnEffect();
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
        return "Mana Burn";
    }

    private static class SingletonHolder {
        private static final ManaBurn INSTANCE = new ManaBurn();
    }
}
