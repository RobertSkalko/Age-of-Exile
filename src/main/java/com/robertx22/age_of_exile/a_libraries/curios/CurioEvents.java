package com.robertx22.age_of_exile.a_libraries.curios;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class CurioEvents {

    public static void reg() {
        ForgeEvents.registerForgeEvent(CurioChangeEvent.class, event -> {

            LivingEntity entity = event.getEntityLiving();
            if (entity != null) {
                if (!entity.level.isClientSide) {
                    EntityData data = Load.Unit(entity);
                    if (data != null) {
                        data.setEquipsChanged(true);
                        data.tryRecalculateStats();

                        if (entity instanceof ServerPlayerEntity) {
                            data.syncToClient((ServerPlayerEntity) entity);
                        }
                    }
                }
            }

        });
    }
}
