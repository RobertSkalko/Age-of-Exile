package com.robertx22.mine_and_slash.vanilla_mc.blocks.slots;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SalvageSlot extends Slot {
    public SalvageSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return TileGearSalvage.isItemValidForInputSlot(stack);
    }
}