package com.robertx22.age_of_exile.a_libraries.curios;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import top.theillusivec4.curios.api.event.CurioChangeCallback;

public class OnCurioChangeEvent implements CurioChangeCallback {

    @Override
    public void change(LivingEntity entity, String id, int index, ItemStack from, ItemStack to) {

        if (entity != null) {
            if (!entity.world.isClient) {
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
    }

}
