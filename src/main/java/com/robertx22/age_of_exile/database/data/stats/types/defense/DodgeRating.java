package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.DodgeEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class DodgeRating extends Stat implements IExtraStatEffect, IUsableStat, ILocalStat {

    public static String GUID = "dodge";

    public static DodgeRating getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore physical damage";
    }

    @Override
    public IStatEffect getEffect() {
        return DodgeEffect.getInstance();
    }

    protected DodgeRating() {
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
        return "Dodge Rating";
    }

    @Override
    public float getMaxMulti() {
        return 0.9F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 200;
    }

    private static class SingletonHolder {
        private static final DodgeRating INSTANCE = new DodgeRating();
    }
}
