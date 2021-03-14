package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropData;

public abstract class SkillEffect extends BaseStatEffect<SkillDropData> {
    public SkillEffect() {
        super(SkillDropData.class);
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }
}
