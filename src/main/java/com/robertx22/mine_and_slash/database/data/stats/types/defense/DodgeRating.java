package com.robertx22.mine_and_slash.database.data.stats.types.defense;

import com.robertx22.mine_and_slash.database.data.stats.ILocalStat;
import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.DodgeEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class DodgeRating extends Stat implements IStatEffects, IUsableStat, ILocalStat {

    public static String GUID = "dodge";

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    public static DodgeRating getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Defenses;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore all damage";
    }

    @Override
    public String getIconPath() {
        return "dodge";
    }

    @Override
    public IStatEffect getEffect() {
        return new DodgeEffect();
    }

    private DodgeRating() {
        this.minimumValue = 0;
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
        return 8;
    }

    private static class SingletonHolder {
        private static final DodgeRating INSTANCE = new DodgeRating();
    }
}
