package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

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

        if (this.data.getResourceType() == ResourceType.health) {
            if (data.getRestoreType() == RestoreType.heal) {
                if (source instanceof PlayerEntity) {
                    float threat = (int) (data.getNumber() * 0.2F);
                    List<MobEntity> mobs = EntityFinder.start(source, MobEntity.class, source.getBlockPos())
                        .radius(10)
                        .build();
                    for (MobEntity x : mobs) {
                        GenerateThreatEvent threatEvent = new GenerateThreatEvent((PlayerEntity) source, x, ThreatGenType.heal, threat);
                        threatEvent.Activate();
                    }
                }
            }
        }

    }
}