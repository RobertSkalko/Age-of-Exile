package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;

public abstract class BaseRegenEffect extends InCodeStatEffect<RestoreResourceEvent> {

    public BaseRegenEffect() {
        super(RestoreResourceEvent.class);
    }
}