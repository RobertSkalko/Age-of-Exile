package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.CriticalHitEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class CriticalHit extends Stat implements IExtraStatEffect {

    public static String GUID = "critical_hit";

    public static CriticalHit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to multiply attack damage by critical damage";
    }

    @Override
    public IStatEffect getEffect() {
        return CriticalHitEffect.getInstance();
    }

    private CriticalHit() {
        this.base_val = 1;
        this.max_val = 100;
        this.min_val = 0;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2694";
        this.textFormat = Formatting.YELLOW;
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
        return "Crit Chance";
    }

    private static class SingletonHolder {
        private static final CriticalHit INSTANCE = new CriticalHit();
    }
}
