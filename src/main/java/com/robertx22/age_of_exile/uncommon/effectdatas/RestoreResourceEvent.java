package com.robertx22.age_of_exile.uncommon.effectdatas;

import net.minecraft.entity.LivingEntity;

public class RestoreResourceEvent extends EffectEvent {

    public static String ID = "on_spend_resource";

    protected RestoreResourceEvent(float num, LivingEntity source, LivingEntity target) {
        super(num, source, target);
    }

    @Override
    public String GUID() {
        return ID;
    }

    @Override
    protected void activate() {

        if (data.isCanceled()) {
            return;
        }

        this.targetData.getResources()
            .restore(target, data.getResourceType(), data.getNumber());

    }
}