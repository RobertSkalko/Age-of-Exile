package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;

public abstract class BaseAnyEffect extends BaseStatEffect<EffectEvent> {
    public BaseAnyEffect() {
        super(EffectEvent.class);
    }
}