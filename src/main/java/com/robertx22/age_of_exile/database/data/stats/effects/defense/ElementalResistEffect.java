package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
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
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

        if (effect.data.getBoolean(EventData.IGNORE_RESIST)) {
            return effect;
        }

        float pene = effect.getPenetration();

        int max = (int) stat.max;

        StatData maxdata = effect.targetData.getUnit()
            .getCalculatedStat(new MaxElementalResist(effect.getElement()));

        max += maxdata.getValue();

        int resist = (int) data.getValue();

        if (resist >= stat.max) {
            resist += maxdata.getValue();
        }

        resist -= pene;

        float resistPercent = MathHelper.clamp(resist, stat.min, max);

        effect.data.getNumber(EventData.NUMBER).number = MathUtils.applyResistMultiplier(effect.data.getNumber(), resistPercent);

        return effect;

    }

    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        if (effect.GetElement() != Elements.Physical) {
            if (effect.GetElement()
                .equals(stat.getElement())) {
                return true;
            }
        }
        return false;
    }

}
