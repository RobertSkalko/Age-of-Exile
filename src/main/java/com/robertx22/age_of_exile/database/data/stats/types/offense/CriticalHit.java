package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.CriticalHitEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class CriticalHit extends Stat implements IStatEffects, ILocalStat {

    public static String GUID = "critical_hit";

    public static CriticalHit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Damage;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to multiply damage by critical damage";
    }

    @Override
    public String getIconPath() {
        return "crit_hit";
    }

    @Override
    public IStatEffect getEffect() {
        return new CriticalHitEffect();
    }

    private CriticalHit() {
        this.BaseFlat = 1;
        this.maximumValue = 100;
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Critical Strike Chance";
    }

    private static class SingletonHolder {
        private static final CriticalHit INSTANCE = new CriticalHit();
    }
}
