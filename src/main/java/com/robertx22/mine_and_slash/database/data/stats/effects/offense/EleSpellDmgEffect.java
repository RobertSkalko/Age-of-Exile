package com.robertx22.mine_and_slash.database.data.stats.effects.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class EleSpellDmgEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Third.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.percentIncrease += data.getAverageValue();

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {

        if (effect.element.equals(Elements.Physical)) {
            return false;
        }

        if (effect.getEffectType()
            .equals(EffectData.EffectTypes.SPELL)) {
            if (stat.getElement() != null && stat.getElement()
                .equals(effect.GetElement())) {
                return true;
            }
        }

        return false;
    }

}
