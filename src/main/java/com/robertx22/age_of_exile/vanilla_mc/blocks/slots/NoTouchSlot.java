package com.robertx22.age_of_exile.vanilla_mc.blocks.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class NoTouchSlot extends Slot {
    public NoTouchSlot(Inventory inventoryIn, int index, int xPosition,
                       int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return false;
    }
}

