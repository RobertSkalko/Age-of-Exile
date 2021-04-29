package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseRegenEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.RegenEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public abstract class BaseRegenClass extends Stat {

    @Override
    public String locDescForLangFile() {
        return "Restores this much every few seconds.";
    }

    public BaseRegenClass() {

        this.statEffect = new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return 0;
            }

            @Override
            public RegenEvent activate(RegenEvent effect, StatData data, Stat stat) {
                effect.data.getNumber(EventData.NUMBER).number += data.getAverageValue();
                return effect;
            }

            @Override
            public boolean canActivate(RegenEvent effect, StatData data, Stat stat) {
                return effect.type == getResourceType();
            }
        };
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    public abstract ResourceType getResourceType();
}
