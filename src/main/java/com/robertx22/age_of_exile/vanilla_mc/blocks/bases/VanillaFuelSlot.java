package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.FurnaceTileEntity;

public class VanillaFuelSlot extends Slot {
    public VanillaFuelSlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return FurnaceTileEntity.getFuel()
            .getOrDefault(stack.getItem(), 0) > 0;
    }

}
