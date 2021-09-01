package com.robertx22.age_of_exile.database.data.stats.effects.game_changers;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class HealthRestorationToBloodEffect extends InCodeStatEffect<RestoreResourceEvent> {

    private HealthRestorationToBloodEffect() {
        super(RestoreResourceEvent.class);
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
    public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {

        float bloodrestored = effect.data.getNumber() * data.getValue() / 100F;

        RestoreResourceEvent restore = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.blood, RestoreType.regen, bloodrestored)
            .build();

        restore.Activate();
        return effect;
    }

    @Override
    public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {

        if (effect.data.isSpellEffect()) {
            return false;
        }
        if (effect.data.getResourceType() == ResourceType.health) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBloodEffect INSTANCE = new HealthRestorationToBloodEffect();
    }
}