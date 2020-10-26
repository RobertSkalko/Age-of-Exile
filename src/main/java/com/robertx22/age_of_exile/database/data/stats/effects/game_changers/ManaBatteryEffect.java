package com.robertx22.age_of_exile.database.data.stats.effects.game_changers;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MagicShieldEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import net.minecraft.util.math.MathHelper;

public class ManaBatteryEffect extends BaseDamageEffect {

    public static final ManaBatteryEffect INSTANCE = new ManaBatteryEffect();

    @Override
    public int GetPriority() {
        return Priority.afterThis(MagicShieldEffect.INSTANCE.GetPriority());
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        float currentMana = effect.targetData.getResources()
            .getMana();

        float maxMana = effect.targetData.getUnit()
            .manaData()
            .getAverageValue();

        float dmgReduced = MathHelper.clamp(effect.number * data.getAverageValue() / 100F, 0, currentMana - (maxMana * 0.5F));

        if (dmgReduced > 0) {

            effect.number -= dmgReduced;

            ResourcesData.Context ctx = new ResourcesData.Context(effect.targetData, effect.target,
                ResourcesData.Type.MANA, dmgReduced,
                ResourcesData.Use.SPEND
            );

            effect.targetData.getResources()
                .modify(ctx);

        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        float currentMana = effect.targetData.getResources()
            .getMana();

        return currentMana / effect.targetData.getUnit()
            .manaData()
            .getAverageValue() > 0.5F;
    }

}
