package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ArmorEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class Armor extends Stat implements IStatEffects, IUsableStat, ILocalStat {

    public static Armor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Defenses;
    }

    @Override
    public String getIconPath() {
        return "armor";
    }

    @Override
    public String locDescForLangFile() {
        return "Decreases damage taken by a percent";
    }

    public static String GUID = "armor";

    private Armor() {
        this.min_val = 0;
        this.scaling = StatScaling.SCALING;
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
    public float getMaxMulti() {
        return 0.8F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 200;
    }

    @Override
    public IStatEffect getEffect() {
        return new ArmorEffect();
    }

    @Override
    public String locNameForLangFile() {
        return "Armor";
    }

    private static class SingletonHolder {
        private static final Armor INSTANCE = new Armor();
    }
}
