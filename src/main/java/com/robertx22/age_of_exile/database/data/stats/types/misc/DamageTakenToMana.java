package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.misc.DamageTakenToManaEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DamageTakenToMana extends Stat implements IStatEffects {

    private DamageTakenToMana() {
    }

    public static DamageTakenToMana getInstance() {
        return DamageTakenToMana.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "% of damage taken is restored to mana.";
    }

    @Override
    public String GUID() {
        return "dmg_taken_to_mana";
    }

    @Override
    public String locNameForLangFile() {
        return "Of Damage Taken to Mana";
    }

    @Override
    public IStatEffect getEffect() {
        return DamageTakenToManaEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final DamageTakenToMana INSTANCE = new DamageTakenToMana();
    }
}
