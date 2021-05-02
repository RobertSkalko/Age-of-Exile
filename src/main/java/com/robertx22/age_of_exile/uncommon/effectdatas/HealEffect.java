package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;

public class HealEffect extends EffectData {

    public static String ID = "on_heal";

    @Override
    public String GUID() {
        return ID;
    }

    public ResourcesData.Context healData;

    public HealEffect(ResourcesData.Context data) {
        super(data.amount, data.source, data.target);
        if (data.spell != null) {
            this.data.setString(EventData.SPELL, data.spell);
        }
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
