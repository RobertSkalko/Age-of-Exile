package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.heals.HealCritChance;
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
        this.base_val = 1;
        this.max_val = 100;
        this.min_val = 0;
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "All Crit Chance";
    }

    @Override
    public void transferStats(EntityCap.UnitData unit, InCalcStatData thisstat) {
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(CriticalHit.getInstance()));
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(SpellCriticalHit.getInstance()));
        thisstat.addFullyTo(unit.getUnit()
            .getStats()
            .getStatInCalculation(HealCritChance.getInstance()));
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final GlobalCriticalHit INSTANCE = new GlobalCriticalHit();
    }
}
