package com.robertx22.mine_and_slash.a_libraries.curios;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;



public class OnCurioChangEvent {
/*
    @SubscribeEvent
    public static void onEquipCurioChange(LivingCurioChangeEvent event) {

        LivingEntity entity = event.getEntityLiving();
        if (entity != null) {
            if (!entity.world.isClient) {
                EntityCap.UnitData data = Load.Unit(entity);
                if (data != null) {
                    data.setEquipsChanged(true);
                    data.tryRecalculateStats(entity);

                    if (entity instanceof ServerPlayerEntity) {
                        data.syncToClient((ServerPlayerEntity) entity);
                    }
                }
            }
        }
    }


 */
}
