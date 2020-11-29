package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.math.MathHelper;

public class DodgeEffect extends BaseDamageEffect {

    protected DodgeEffect() {
    }

    public static DodgeEffect getInstance() {
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
        DodgeRating dodge = (DodgeRating) stat;

        float totalDodge = MathHelper.clamp(data.getAverageValue() - effect.attackerAccuracy, 0, Integer.MAX_VALUE);

        float chance = dodge.getUsableValue((int) totalDodge, effect.sourceData.getLevel()) * 100;

        if (RandomUtils.roll(chance)) {
            effect.number = 0;
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
        if (effect.element == Elements.Physical) {
            if (EffectUtils.isConsideredAWeaponAttack(effect)) {
                return true;
            }
        }
        return false;
    }

    private static class SingletonHolder {
        private static final DodgeEffect INSTANCE = new DodgeEffect();
    }
}
