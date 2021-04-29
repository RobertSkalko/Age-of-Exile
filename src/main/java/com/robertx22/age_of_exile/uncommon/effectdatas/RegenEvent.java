package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import net.minecraft.entity.LivingEntity;

public class RegenEvent extends EffectData {

    public ResourceType type;

    public RegenEvent(LivingEntity source, LivingEntity target, ResourceType type) {
        super(source, target);
        this.type = type;
    }

    @Override
    protected void activate() {

        if (data.isCanceled()) {
            return;
        }

        ResourcesData.Context ctx = new ResourcesData.Context(targetData, target, type,
            data.getNumber(),
            ResourcesData.Use.RESTORE
        );
        targetData.getResources()
            .modify(ctx);
    }
}
