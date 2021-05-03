package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import net.minecraft.entity.LivingEntity;

public class RestoreResourceEvent extends EffectEvent {

    public static String ID = "on_spend_resource";

    @Override
    public String GUID() {
        return ID;
    }

    public RestoreResourceEvent(LivingEntity source, LivingEntity target, ResourceType resource, RestoreType restoreType, float amount) {
        super(amount, source, target);

        this.data.setString(EventData.RESOURCE_TYPE, resource.name());
        this.data.setString(EventData.RESTORE_TYPE, restoreType.name());
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