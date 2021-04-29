package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IArmorReducable;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IPenetrable;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.math.MathHelper;

public class ArmorEffect extends BaseStatEffect<DamageEffect> {

    public ArmorEffect() {
        super(DamageEffect.class);
    }

    @Override
    public int GetPriority() {
        return Priority.Third.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        int pene = 0;

        if (effect instanceof IPenetrable) {
            IPenetrable ipen = (IPenetrable) effect;
            pene = ipen.GetArmorPenetration();
        }

        IUsableStat armor = (IUsableStat) stat;

        float EffectiveArmor = armor.getUsableValue((int) (data.getAverageValue() - pene), effect.sourceData.getLevel());

        EffectiveArmor = MathHelper.clamp(EffectiveArmor, 0, 1);

        effect.data.getNumber(EventData.NUMBER).number -= EffectiveArmor * effect.data.getNumber();

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect instanceof IArmorReducable && effect.GetElement() == Elements.Physical;
    }

}
