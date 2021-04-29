package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.defense.SpellDodge;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.math.MathHelper;

public class SpellDodgeEffect extends BaseDamageEffect {

    protected SpellDodgeEffect() {
    }

    public static SpellDodgeEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.afterThis(Accuracy.getInstance().statEffect
            .GetPriority());
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        SpellDodge dodge = (SpellDodge) stat;

        float totalDodge = MathHelper.clamp(data.getAverageValue() - effect.attackerAccuracy, 0, Integer.MAX_VALUE);

        float chance = dodge.getUsableValue((int) totalDodge, effect.sourceData.getLevel()) * 100;

        if (RandomUtils.roll(chance)) {
            effect.data.getNumber(EventData.NUMBER).number = 0;
            effect.isDodged = true;
        } else {
            if (RandomUtils.roll(chance)) {
                effect.accuracyCritRollFailed = true;
            }
        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        if (effect.attackType.isSpell()) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final SpellDodgeEffect INSTANCE = new SpellDodgeEffect();
    }
}
