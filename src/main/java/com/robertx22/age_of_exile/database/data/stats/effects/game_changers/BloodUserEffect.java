package com.robertx22.age_of_exile.database.data.stats.effects.game_changers;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;

public class BloodUserEffect extends BaseStatEffect<ModifyResourceEffect> {

    private BloodUserEffect() {
        super(ModifyResourceEffect.class);
    }

    public static BloodUserEffect getInstance() {
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

        effect.ctx.type = ResourceType.BLOOD;

        return effect;
    }

    @Override
    public boolean canActivate(ModifyResourceEffect effect, StatData data, Stat stat) {

        if (effect.ctx.use == ResourcesData.Use.SPEND) {
            if (effect.ctx.amount > 0) {
                if (effect.ctx.type == ResourceType.MANA) {
                    return true;
                }
            }
        }

        return false;
    }

    private static class SingletonHolder {
        private static final BloodUserEffect INSTANCE = new BloodUserEffect();
    }
}