package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class GlobalCriticalDamage extends Stat implements ITransferToOtherStats {

    public static String GUID = "global_critical_damage";

    public static GlobalCriticalDamage getInstance() {
        return GlobalCriticalDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects critical hit damage";
    }

    private GlobalCriticalDamage() {
        this.base = 1;
        this.max = 500;
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
        return "All Crit Damage";
    }

    @Override
    public void transferStats(EntityCap.UnitData unit, InCalcStatData thisstat) {
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(Stats.CRIT_DAMAGE.get()));
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(Stats.SPELL_CRIT_DAMAGE.get()));
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(Stats.HEAL_CRIT_DAMAGE.get()));
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final GlobalCriticalDamage INSTANCE = new GlobalCriticalDamage();
    }
}

