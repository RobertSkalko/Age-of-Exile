package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.DmgAtNightEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DmgAtNightStat extends Stat implements IStatEffects {

    public static String GUID = "night_dmg";

    private DmgAtNightStat() {
        this.scaling = StatScaling.NONE;
    }

    public static DmgAtNightStat getInstance() {
        return DmgAtNightStat.SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Damage;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases dmg at night.";
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Damage at Night";
    }

    @Override
    public IStatEffect getEffect() {
        return DmgAtNightEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final DmgAtNightStat INSTANCE = new DmgAtNightStat();
    }
}
