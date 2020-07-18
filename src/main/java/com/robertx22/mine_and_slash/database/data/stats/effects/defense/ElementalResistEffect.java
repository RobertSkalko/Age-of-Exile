package com.robertx22.mine_and_slash.database.data.stats.effects.defense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IElementalEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IElementalPenetrable;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IElementalResistable;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.MathUtils;
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

        int pene = 0;

        if (effect instanceof IElementalPenetrable) {
            IElementalPenetrable ipen = (IElementalPenetrable) effect;
            pene = ipen.GetElementalPenetration();
        }

        float resistPercent = MathHelper.clamp(data.getAverageValue() - pene, stat.minimumValue, stat.maximumValue);

        effect.number = MathUtils.applyResistMultiplier(effect.number, resistPercent);

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
