package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class GlobalCriticalHit extends Stat implements ITransferToOtherStats {

    public static String GUID = "global_critical_hit";

    public static GlobalCriticalHit getInstance() {
        return GlobalCriticalHit.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to multiply attack damage by critical damage";
    }

    private GlobalCriticalHit() {
        this.base = 1;
        this.max = 100;
        this.min = 0;
        this.group = StatGroup.MAIN;
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "All Crit Chance";
    }

    @Override
    public void transferStats(EntityData unit, InCalcStatData thisstat) {
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(Stats.CRIT_CHANCE.get()));
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(Stats.SPELL_CRIT_CHANCE.get()));
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(Stats.HEAL_CRIT_CHANCE.get()));
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final GlobalCriticalHit INSTANCE = new GlobalCriticalHit();
    }
}
