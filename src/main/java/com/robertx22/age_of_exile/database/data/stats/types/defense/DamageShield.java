package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.DamageShieldEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DamageShield extends Stat implements IStatEffects {

    private DamageShield() {
        this.scaling = StatScaling.SCALING;
    }

    public static DamageShield getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public IStatEffect getEffect() {
        return DamageShieldEffect.getInstance();
    }

    @Override
    public String GUID() {
        return "damage_shield";
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Decreases that amount of damage from every attack.";
    }

    @Override
    public String locNameForLangFile() {
        return "Damage Shield";
    }

    private static class SingletonHolder {
        private static final DamageShield INSTANCE = new DamageShield();
    }
}
