package com.robertx22.age_of_exile.database.data.stats.effects.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class DamageTakenToManaEffect extends BaseStatEffect<DamageEffect> {

    public DamageTakenToManaEffect() {
        super(DamageEffect.class);
    }

    public static DamageTakenToManaEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

        float restore = effect.number * data.getAverageValue() / 100F; // todo dmg number

        if (restore > 0) {
            ResourcesData.Context mana = new ResourcesData.Context(effect.targetData, effect.target,
                ResourceType.MANA,
                restore,
                ResourcesData.Use.RESTORE
            );
            effect.targetData.getResources()
                .modify(mana);
        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return true;
    }

    private static class SingletonHolder {
        private static final DamageTakenToManaEffect INSTANCE = new DamageTakenToManaEffect();
    }
}
