package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class VanillaFuelSlot extends Slot {
    public VanillaFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return FurnaceBlockEntity.createFuelTimeMap()
            .getOrDefault(stack.getItem(), 0) > 0;
    }

}
