package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.spell_calc.ReduceCooldownEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class CooldownReduction extends Stat {

    private CooldownReduction() {
        this.max = 75;
        this.statEffect = new ReduceCooldownEffect();
    }

    public static CooldownReduction getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Reduces spell cooldown.";
    }

    @Override
    public String locNameForLangFile() {
        return "Cooldown Reduction";
    }

    @Override
    public String GUID() {
        return "cdr";
    }

    private static class SingletonHolder {
        private static final CooldownReduction INSTANCE = new CooldownReduction();
    }
}
