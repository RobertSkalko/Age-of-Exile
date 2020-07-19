package com.robertx22.mine_and_slash.vanilla_mc.blocks.slots;

import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class AnyModifySlot extends Slot {
    public AnyModifySlot(Inventory inventoryIn, int index, int xPosition,
                         int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return ItemFilterGroup.ANY_MODIFY.anyMatchesFilter(stack);
    }
}
