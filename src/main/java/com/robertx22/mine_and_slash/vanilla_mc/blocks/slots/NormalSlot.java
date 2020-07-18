package com.robertx22.mine_and_slash.vanilla_mc.blocks.slots;

import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class NormalSlot extends Slot {
    public NormalSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return true;
    }

}