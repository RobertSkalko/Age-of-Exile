package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

public class SpendResourceEvent extends EffectEvent {

    public static String ID = "on_spend_resource";

    @Override
    public String GUID() {
        return ID;
    }

    public SpendResourceEvent(LivingEntity en, ResourceType resource, float amount) {
        super(amount, en, en);
        this.data.setString(EventData.RESOURCE_TYPE, resource.name());

    }

    @Override
    protected void activate() {

        if (data.isCanceled()) {
            return;
        }

        this.targetData.getResources()
            .spend(target, data.getResourceType(), data.getNumber());

    }
}
