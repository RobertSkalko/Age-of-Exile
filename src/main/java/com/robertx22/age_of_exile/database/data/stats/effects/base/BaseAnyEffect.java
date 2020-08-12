package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public abstract class BaseAnyEffect extends BaseStatEffect<EffectData> {
    public BaseAnyEffect() {
        super(EffectData.class);
    }
}