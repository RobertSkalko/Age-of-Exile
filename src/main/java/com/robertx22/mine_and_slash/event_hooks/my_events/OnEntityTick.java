package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.exile_lib.events.base.EventConsumer;
import com.robertx22.exile_lib.events.base.ExileEvents;
import com.robertx22.mine_and_slash.capability.bases.EntityGears;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class OnEntityTick extends EventConsumer<ExileEvents.OnEntityTick> {

    @Override
    public void accept(ExileEvents.OnEntityTick onEntityTick) {
        LivingEntity entity = onEntityTick.entity;

        if (entity.world.isClient) {
            return;
        }
        checkGearChanged(entity);
    }

    public static void checkGearChanged(LivingEntity entity) {

        if (entity.world.isClient) {
            return;
        }

        EntityCap.UnitData data = Load.Unit(entity);

        EntityGears gears = data.getCurrentGears();

        boolean calc = false;

        ItemStack chestnow = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (gears.my$chest == null || !ItemStack.areEqual(chestnow, gears.my$chest)) {
            gears.my$chest = chestnow;
            calc = true;
        }
        ItemStack legsnow = entity.getEquippedStack(EquipmentSlot.LEGS);
        if (gears.my$legs == null || !ItemStack.areEqual(legsnow, gears.my$legs)) {
            gears.my$legs = legsnow;
            calc = true;
        }
        ItemStack feetnow = entity.getEquippedStack(EquipmentSlot.FEET);
        if (gears.my$feet == null || !ItemStack.areEqual(feetnow, gears.my$feet)) {
            gears.my$feet = feetnow;
            calc = true;
        }
        ItemStack headnow = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (gears.my$head == null || !ItemStack.areEqual(headnow, gears.my$head)) {
            gears.my$head = headnow;
            calc = true;
        }
        ItemStack weaponnow = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (gears.my$weapon == null || !ItemStack.areEqual(weaponnow, gears.my$weapon)) {
            gears.my$weapon = weaponnow;
            calc = true;
        }
        ItemStack offhandnow = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (gears.my$offhand == null || !ItemStack.areEqual(offhandnow, gears.my$offhand)) {
            gears.my$offhand = offhandnow;
            calc = true;

        }

        if (calc) {
            on$change(entity);
        }

    }

    private static void on$change(LivingEntity entity) {
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
