package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;

public class HealEffect extends EffectData {

    public ResourcesData.Context healData;

    public boolean isCrit = false;

    public HealEffect(ResourcesData.Context data) {
        super(data.amount, data.source, data.target);
        this.healData = data;
    }

    @Override
    protected void activate() {

        if (this.data.isCanceled()) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();

            HealthUtils.heal(target, data.getNumber());
        }
    }

}
