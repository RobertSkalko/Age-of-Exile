package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.DmgAtDayEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DmgAtDayStat extends Stat implements IStatEffects {

    public static String GUID = "day_dmg";

    private DmgAtDayStat() {
        this.scaling = StatScaling.NONE;
    }

    public static DmgAtDayStat getInstance() {
        return DmgAtDayStat.SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Damage;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases dmg at day.";
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
        return DmgAtDayEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final DmgAtDayStat INSTANCE = new DmgAtDayStat();
    }
}

