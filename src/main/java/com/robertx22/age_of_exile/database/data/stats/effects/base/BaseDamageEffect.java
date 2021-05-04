package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;

public abstract class BaseDamageEffect extends BaseStatEffect<DamageEvent> {
    public BaseDamageEffect() {
        super(DamageEvent.class);
    }
}