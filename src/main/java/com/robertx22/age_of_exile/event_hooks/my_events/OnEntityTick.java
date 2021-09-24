package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.bases.EntityGears;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.dimension.rules.OnTickCancelTargettingOtherMobs;
import com.robertx22.age_of_exile.dimension.rules.OnTickDespawnIfFailedOrEmpty;
import com.robertx22.age_of_exile.dimension.rules.OnTickRegenerate;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class OnEntityTick extends EventConsumer<ExileEvents.OnEntityTick> {

    @Override
    public void accept(ExileEvents.OnEntityTick onEntityTick) {
        LivingEntity entity = onEntityTick.entity;

        if (entity.level.isClientSide) {
            return;
        }
        try {
            if (Load.Unit(entity) == null) {
                return; // it shouldnt be though
            }

            Load.Unit(entity)
                .getCooldowns()
                .onTicksPass(1);

            if (entity instanceof MobEntity) {
                OnTickCancelTargettingOtherMobs.cancelTarget((MobEntity) entity);
                OnTickDespawnIfFailedOrEmpty.despawn((MobEntity) entity);
            }

            OnTickRegenerate.regen(40, entity);

            if (entity instanceof PlayerEntity == false) {
                if (entity.tickCount % 40 != 0) {
                    return; // dont check gear of mobs as often as players
                }
            }

            checkGearChanged(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkGearChanged(LivingEntity entity) {

        if (entity.level.isClientSide) {
            return;
        }

        EntityData data = Load.Unit(entity);

        EntityGears gears = data.getCurrentGears();

        boolean calc = false;

        for (EquipmentSlotType s : EquipmentSlotType.values()) {
            ItemStack now = entity.getItemBySlot(s);
            ItemStack before = gears.get(s);

            if (now != before) {
                calc = true;
            }
            gears.put(s, now);
        }

        if (calc) {
            on$change(entity);
        }

    }

    private static void on$change(LivingEntity entity) {
        if (entity != null) {

            EntityData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            data.tryRecalculateStats();

            if (entity instanceof PlayerEntity) {
                data.syncToClient((PlayerEntity) entity);
            }
        }

    }

}
