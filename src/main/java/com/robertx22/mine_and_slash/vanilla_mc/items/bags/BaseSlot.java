package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import net.minecraft.container.Slot;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;

public abstract class BaseSlot extends Slot {

    public BaseSlot(BasicInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);

    }

    public abstract boolean canInsert(ItemStack stack);

}
