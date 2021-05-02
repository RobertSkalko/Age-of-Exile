package com.robertx22.age_of_exile.database.data.stats.effects.game_changers;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class HealthRestorationToBloodEffect extends BaseStatEffect<ModifyResourceEffect> {

    private HealthRestorationToBloodEffect() {
        super(ModifyResourceEffect.class);
    }

    public static HealthRestorationToBloodEffect getInstance() {
        return SingletonHolder.INSTANCE;
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

        float bloodrestored = effect.ctx.amount * data.getAverageValue() / 100F;

        ResourcesData.Context blood = new ResourcesData.Context(effect.ctx.targetData, effect.ctx.target,
            ResourceType.BLOOD, bloodrestored,
            ResourcesData.Use.RESTORE
        );
        effect.ctx.targetData.getResources()
            .modify(blood);

        return effect;
    }

    @Override
    public boolean canActivate(ModifyResourceEffect effect, StatData data, Stat stat) {

        if (effect.data.isSpellEffect()) {
            return false;
        }

        if (effect.ctx.use == ResourcesData.Use.RESTORE) {
            if (effect.ctx.amount > 0) {
                if (effect.ctx.type == ResourceType.HEALTH) {
                    return true;

                }
            }
        }
        return false;
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBloodEffect INSTANCE = new HealthRestorationToBloodEffect();
    }
}