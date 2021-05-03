package com.robertx22.age_of_exile.database.data.stats.effects.game_changers;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class BloodUserEffect extends BaseStatEffect<SpendResourceEvent> {

    private BloodUserEffect() {
        super(SpendResourceEvent.class);
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
    public SpendResourceEvent activate(SpendResourceEvent effect, StatData data, Stat stat) {
        effect.data.setString(EventData.RESOURCE_TYPE, ResourceType.blood.name());
        return effect;
    }

    @Override
    public boolean canActivate(SpendResourceEvent effect, StatData data, Stat stat) {
        if (effect.data.getResourceType() == ResourceType.mana) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final BloodUserEffect INSTANCE = new BloodUserEffect();
    }
}