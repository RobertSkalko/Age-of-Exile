package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.util.math.MathHelper;

public class ArmorEffect extends InCodeStatEffect<DamageEvent> {

    public ArmorEffect() {
        super(DamageEvent.class);
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
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
        float pene = effect.getPenetration();

        IUsableStat armor = (IUsableStat) stat;

        float EffectiveArmor = armor.getUsableValue((int) (data.getValue() - pene), effect.sourceData.getLevel());

        EffectiveArmor = MathHelper.clamp(EffectiveArmor, 0, 1);

        effect.data.getNumber(EventData.NUMBER).number -= EffectiveArmor * effect.data.getNumber();

        return effect;
    }

    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        return effect.GetElement() == Elements.Physical;
    }

}
