package com.robertx22.age_of_exile.database.data.stats.effects.resource;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;

public class HealGoesToMagicShieldEffect extends BaseStatEffect<ModifyResourceEffect> {

    private HealGoesToMagicShieldEffect() {
        super(ModifyResourceEffect.class);
    }

    public static HealGoesToMagicShieldEffect getInstance() {
        return HealGoesToMagicShieldEffect.SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public ModifyResourceEffect activate(ModifyResourceEffect effect, StatData data, Stat stat) {

        float taken = effect.ctx.amount * data.getAverageValue() / 100F;

        ResourcesData.Context ms = new ResourcesData.Context(effect.ctx.targetData, effect.ctx.target,
            ResourcesData.Type.MAGIC_SHIELD, taken,
            ResourcesData.Use.RESTORE
        );
        effect.ctx.targetData.getResources()
            .modify(ms);

        effect.number -= taken;

        return effect;
    }

    @Override
    public boolean canActivate(ModifyResourceEffect effect, StatData data, Stat stat) {
        if (effect.ctx.use == ResourcesData.Use.RESTORE) {
            if (effect.ctx.amount > 0) {
                if (effect.ctx.type == ResourcesData.Type.HEALTH) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class SingletonHolder {
        private static final HealGoesToMagicShieldEffect INSTANCE = new HealGoesToMagicShieldEffect();
    }
}
