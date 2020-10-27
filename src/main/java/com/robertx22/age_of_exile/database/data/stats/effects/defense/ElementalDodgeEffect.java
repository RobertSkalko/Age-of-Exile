package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ElementalDodge;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class ElementalDodgeEffect extends BaseDamageEffect {

    private ElementalDodgeEffect() {
    }

    public static ElementalDodgeEffect getInstance() {
        return ElementalDodgeEffect.SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.AlmostLast.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        ElementalDodge dodge = (ElementalDodge) stat;

        float chance = dodge.getUsableValue((int) data.getAverageValue(), effect.sourceData.getLevel()) * 100;

        if (RandomUtils.roll(chance)) {
            DamageEffect dmgeffect = (DamageEffect) effect;

            effect.number = 0;
            dmgeffect.isDodged = true;

        }
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return !effect.element.isPhysical();
    }

    private static class SingletonHolder {
        private static final ElementalDodgeEffect INSTANCE = new ElementalDodgeEffect();
    }
}
