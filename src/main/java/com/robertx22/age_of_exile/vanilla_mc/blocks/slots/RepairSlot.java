package com.robertx22.age_of_exile.vanilla_mc.blocks.slots;

import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilterGroup;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class RepairSlot extends Slot {
    public RepairSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return ItemFilterGroup.LOOT_BAG.anyMatchesFilter(stack);
    }
}
