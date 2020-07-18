package com.robertx22.mine_and_slash.database.data.stats.effects.defense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData.EffectTypes;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class DodgeEffect extends BaseDamageEffect {

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
        DodgeRating dodge = (DodgeRating) stat;

        float chance = dodge.getUsableValue(effect.targetData.getLevel(), (int) data.getAverageValue()) * 100;

        if (RandomUtils.roll(chance)) {
            DamageEffect dmgeffect = (DamageEffect) effect;

            effect.number = 0;
            dmgeffect.isDodged = true;

        }
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return !effect.getEffectType()
            .equals(EffectTypes.SPELL);
    }

}
