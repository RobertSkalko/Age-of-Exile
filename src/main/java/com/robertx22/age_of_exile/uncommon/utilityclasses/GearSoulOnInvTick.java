package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class GearSoulOnInvTick {

    public static void checkAndGenerate(PlayerEntity player) {

        try {

            if (player.level.isClientSide) {
                return;
            }

            for (ItemStack stack : player.inventory.items) {
                tryStack(player, stack);
            }
            for (ItemStack stack : player.inventory.armor) {
                tryStack(player, stack);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void tryStack(PlayerEntity player, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        if (StackSaving.STAT_SOULS.has(stack)) {
            StatSoulData soul = StackSaving.STAT_SOULS.loadFrom(stack);

            if (soul != null && soul.canInsertIntoStack(stack)) {
                try {
                    GearItemData gear = soul.createGearData(stack);
                    stack.removeTagKey(StatSoulItem.TAG);
                    gear.saveToStack(stack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
