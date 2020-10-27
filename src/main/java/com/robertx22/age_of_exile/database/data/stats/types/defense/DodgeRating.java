package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.DodgeEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DodgeRating extends Stat implements IStatEffects, IUsableStat, ILocalStat {

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
        return new DodgeEffect();
    }

    private DodgeRating() {
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
    public String locNameForLangFile() {
        return "Dodge Rating";
    }

    @Override
    public float getMaxMulti() {
        return 0.9F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 250;
    }

    private static class SingletonHolder {
        private static final DodgeRating INSTANCE = new DodgeRating();
    }
}
