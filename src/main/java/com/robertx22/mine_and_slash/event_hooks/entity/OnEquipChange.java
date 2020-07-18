package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnEquipChange {

    // on change, notify my capability so i know stats need to be recalculated
    @SubscribeEvent
    public static void onEquipChange(LivingEquipmentChangeEvent event) {

        LivingEntity entity = event.getEntityLiving();

        if (entity != null) {

            EntityCap.UnitData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            data.tryRecalculateStats(entity);

            if (entity instanceof PlayerEntity) {
                data.syncToClient((PlayerEntity) entity);
            }
        }

    }

}
