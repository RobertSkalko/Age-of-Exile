package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.GiveShieldEvent;

public abstract class BaseShieldEffect extends BaseStatEffect<GiveShieldEvent> {

    public BaseShieldEffect() {
        super(GiveShieldEvent.class);
    }
}
