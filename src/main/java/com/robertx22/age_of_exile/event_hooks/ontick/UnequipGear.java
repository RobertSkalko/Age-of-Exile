package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Arrays;
import java.util.List;

public class UnequipGear {

    // dont drop weapons becasuse then newbies can't use stuff like axes at low level!

    public static List<EquipmentSlotType> SLOTS = Arrays.asList(EquipmentSlotType.FEET, EquipmentSlotType.LEGS, EquipmentSlotType.CHEST, EquipmentSlotType.HEAD, EquipmentSlotType.OFFHAND);

    static void drop(PlayerEntity player, EquipmentSlotType slot, ItemStack stack, IFormattableTextComponent txt) {
        ItemStack copy = stack.copy();

        player.setItemSlot(slot, ItemStack.EMPTY); // todo is this good?

        if (player.getItemBySlot(slot)
            .isEmpty()) {
            PlayerUtils.giveItem(copy, player);
            player.displayClientMessage(txt
                , false);
        } else {
            player.setItemSlot(slot, copy);
            System.out.print("Error in unequipping gear, weird!!!");
        }
    }

    static void drop(PlayerEntity player, ICurioStacksHandler handler, int number, ItemStack stack, IFormattableTextComponent txt) {

        ItemStack copy = stack.copy();
        handler.getStacks()
            .setStackInSlot(number, ItemStack.EMPTY);
        PlayerUtils.giveItem(copy, player);
        player.displayClientMessage(txt, false);
    }

    public static void onTick(PlayerEntity player) {

        int runewords = 0;
        int uniques = 0;

        for (EquipmentSlotType slot : SLOTS) {

            ItemStack stack = player.getItemBySlot(slot);

            GearItemData gear = Gear.Load(stack);

            if (gear != null) {
                if (!gear.canPlayerWear(Load.Unit(player))) {
                    drop(player, slot, stack, new StringTextComponent("You do not meet the requirements of that item.").withStyle(TextFormatting.RED));
                } else if (gear.isUnique()) {
                    uniques++;
                    if (uniques > ServerContainer.get().MAX_UNIQUE_GEARS_WORN.get()) {
                        drop(player, slot, stack, new StringTextComponent("You cannot equip that many unique items.").withStyle(TextFormatting.RED));
                    }
                }
            }
        }

        for (ICurioStacksHandler handler : MyCurioUtils.getHandlers(player)) {

            for (int i = 0; i < handler
                .getSlots(); i++) {

                ItemStack stack = handler
                    .getStacks()
                    .getStackInSlot(i);

                if (!stack.isEmpty()) {
                    GearItemData gear = Gear.Load(stack);

                    if (gear != null) {
                        if (!gear.canPlayerWear(Load.Unit(player))) {
                            drop(player, handler, i, stack, new StringTextComponent("You do not meet the requirements of that item.").withStyle(TextFormatting.RED));
                        } else if (gear.isUnique()) {
                            uniques++;
                            if (uniques > ServerContainer.get().MAX_UNIQUE_GEARS_WORN.get()) {
                                drop(player, handler, i, stack, new StringTextComponent("You cannot equip that many unique items.").withStyle(TextFormatting.RED));
                            }
                        }
                    }
                }

            }

        }
    }
}
