package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.RegenEvent;

public abstract class BaseRegenEffect extends BaseStatEffect<RegenEvent> {

    public BaseRegenEffect() {
        super(RegenEvent.class);
    }
}