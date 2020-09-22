package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.spell_calc.ReduceManaCostEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ReducedManaCost extends Stat implements IStatEffects {

    private ReducedManaCost() {
        this.max_val = 75;
    }

    public static ReducedManaCost getInstance() {
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
        return "Reduces Mana Cost of all spells.";
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Cost Reduction";
    }

    @Override
    public String GUID() {
        return "mana_cost_red";
    }

    @Override
    public IStatEffect getEffect() {
        return new ReduceManaCostEffect();
    }

    private static class SingletonHolder {
        private static final ReducedManaCost INSTANCE = new ReducedManaCost();
    }
}
