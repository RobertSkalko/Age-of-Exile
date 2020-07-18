package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;

public abstract class BaseAnyEffect extends BaseStatEffect<EffectData> {
    public BaseAnyEffect() {
        super(EffectData.class);
    }
}