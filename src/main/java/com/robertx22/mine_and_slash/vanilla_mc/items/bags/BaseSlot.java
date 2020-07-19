package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public abstract class BaseSlot extends Slot {

    public BaseSlot(SimpleInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);

    }

    public abstract boolean canInsert(ItemStack stack);

}
