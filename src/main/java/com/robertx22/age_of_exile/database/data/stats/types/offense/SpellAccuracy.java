package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.SpellAccuracyEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class SpellAccuracy extends Stat implements IStatEffects {

    public static String GUID = "spell_accuracy";

    public static SpellAccuracy getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects chance to hit with magic spells.";
    }

    @Override
    public IStatEffect getEffect() {
        return SpellAccuracyEffect.getInstance();
    }

    private SpellAccuracy() {
        this.base_val = 0;
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
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Accuracy";
    }

    private static class SingletonHolder {
        private static final SpellAccuracy INSTANCE = new SpellAccuracy();
    }
}
