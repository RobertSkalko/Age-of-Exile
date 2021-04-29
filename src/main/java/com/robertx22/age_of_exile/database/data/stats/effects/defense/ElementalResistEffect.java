package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IElementalEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IElementalPenetrable;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IElementalResistable;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MathUtils;
import net.minecraft.util.math.MathHelper;

public class ElementalResistEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Fifth.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

        if (effect.ignoresResists) {
            return effect;
        }

        int pene = 0;

        if (effect instanceof IElementalPenetrable) {
            IElementalPenetrable ipen = (IElementalPenetrable) effect;
            pene = ipen.GetElementalPenetration();
        }

        int max = (int) stat.max_val;

        StatData maxdata = effect.targetData.getUnit()
            .getCalculatedStat(new MaxElementalResist(effect.element));

        max += maxdata.getAverageValue();

        float resistPercent = MathHelper.clamp(data.getAverageValue() - pene, stat.min_val, max);

        effect.data.getNumber(EventData.NUMBER).number = MathUtils.applyResistMultiplier(effect.data.getNumber(), resistPercent);

        return effect;

    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        if (effect instanceof IElementalResistable) {

            IElementalEffect ele = (IElementalEffect) effect;

            if (ele.GetElement() != Elements.Physical) {
                if (ele.GetElement()
                    .equals(stat.getElement())) {
                    return true;
                }
            }

        }
        return false;
    }

}
