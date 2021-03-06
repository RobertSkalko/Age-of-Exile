package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;
import java.util.List;

public class AllAttributes extends Stat implements ITransferToOtherStats {

    private AllAttributes() {
        this.scaling = StatScaling.LINEAR;
    }

    public static AllAttributes getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    public List<BaseCoreStat> coreStatsThatBenefit() {
        return Arrays.asList(Dexterity.INSTANCE, Intelligence.INSTANCE, Strength.INSTANCE, Wisdom.INSTANCE, Vitality.INSTANCE, Agility.INSTANCE);
    }

    @Override
    public String locDescForLangFile() {
        return "Adds to DEX, INT STR, VIT, WIL, AGI.";
    }

    @Override
    public String locNameForLangFile() {
        return "All Attributes";
    }

    @Override
    public String GUID() {
        return "all_attributes";
    }

    @Override
    public void transferStats(EntityCap.UnitData unit, InCalcStatData thisstat) {
        for (BaseCoreStat ele : coreStatsThatBenefit()) {
            thisstat.addFullyTo(unit.getUnit()
                .getStatInCalculation(ele));
        }
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final AllAttributes INSTANCE = new AllAttributes();
    }
}



