package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public abstract class SkillEffect extends InCodeStatEffect<SkillDropEvent> {
    public SkillEffect() {
        super(SkillDropEvent.class);
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }
}
