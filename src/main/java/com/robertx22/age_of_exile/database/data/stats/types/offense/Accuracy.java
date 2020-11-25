package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AccuracyEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class Accuracy extends Stat implements IStatEffects {

    public static String GUID = "accuracy";

    public static Accuracy getInstance() {
        return Accuracy.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your chance to hit, also affects chance to crit. Specifically it decreases opponent's chance to dodge";
    }

    @Override
    public IStatEffect getEffect() {
        return AccuracyEffect.getInstance();
    }

    private Accuracy() {
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
        return "Accuracy";
    }

    private static class SingletonHolder {
        private static final Accuracy INSTANCE = new Accuracy();
    }
}
