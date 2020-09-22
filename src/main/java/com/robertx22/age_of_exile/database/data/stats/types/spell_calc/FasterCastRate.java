package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.spell_calc.ReduceCastTimeEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class FasterCastRate extends Stat implements IStatEffects {

    private FasterCastRate() {
        this.max_val = 75;
    }

    public static FasterCastRate getInstance() {
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
        return "Reduces amount of time needed to cast spells.";
    }

    @Override
    public String locNameForLangFile() {
        return "Faster Cast Rate";
    }

    @Override
    public String GUID() {
        return "faster_cast_rate";
    }

    @Override
    public IStatEffect getEffect() {
        return new ReduceCastTimeEffect();
    }

    private static class SingletonHolder {
        private static final FasterCastRate INSTANCE = new FasterCastRate();
    }
}

