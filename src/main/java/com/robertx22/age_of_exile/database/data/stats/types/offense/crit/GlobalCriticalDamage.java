package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.heals.HealCritStrength;
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
        this.base_val = 1;
        this.max_val = 500;
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
        return "All Crit Damage";
    }

    @Override
    public void transferStats(EntityCap.UnitData unit, InCalcStatData thisstat) {
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(CriticalDamage.getInstance()));
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(SpellCriticalDamage.getInstance()));
        thisstat.addFullyTo(unit.getUnit()
            .getStatInCalculation(HealCritStrength.getInstance()));
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final GlobalCriticalDamage INSTANCE = new GlobalCriticalDamage();
    }
}

