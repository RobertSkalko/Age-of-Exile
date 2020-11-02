package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Arrays;
import java.util.List;

public class UnequipHighLevelGear {

    public static List<EquipmentSlot> SLOTS = Arrays.asList(EquipmentSlot.MAINHAND, EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.OFFHAND);

    public static void onTick(PlayerEntity player) {

        int lvl = Load.Unit(player)
            .getLevel();

        for (EquipmentSlot slot : SLOTS) {

            ItemStack stack = player.getEquippedStack(slot);

            GearItemData gear = Gear.Load(stack);

            if (gear != null) {
                if (lvl < gear.level) {

                    ItemStack copy = stack.copy();

                    player.equipStack(slot, ItemStack.EMPTY); // todo is this good?

                    if (player.getEquippedStack(slot)
                        .isEmpty()) {

                        player.dropItem(copy, false);

                        player.sendMessage(
                            new LiteralText("You are too low level to use that item.").formatted(Formatting.RED)
                            , false);
                    } else {
                        player.equipStack(slot, copy);
                        System.out.print("Error in unequipping gear, weird!!!");
                    }
                }
            }
        }

        for (ICurioStacksHandler handler : MyCurioUtils.getHandlers(player)) {

            for (int i = 0; i < handler
                .getSlots(); i++) {

                ItemStack stack = handler
                    .getStacks()
                    .getStack(i);

                if (!stack.isEmpty()) {
                    GearItemData gear = Gear.Load(stack);

                    if (gear != null) {
                        if (lvl < gear.level) {
                            ItemStack copy = stack.copy();
                            handler.getStacks()
                                .setStack(i, ItemStack.EMPTY);
                            player.dropItem(copy, false);
                            player.sendMessage(
                                new LiteralText("You are too low level to use that item.").formatted(Formatting.RED)
                                , false);
                        }
                    }
                }

            }

        }
    }
}
