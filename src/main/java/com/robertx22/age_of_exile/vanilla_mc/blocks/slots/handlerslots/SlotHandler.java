package com.robertx22.age_of_exile.vanilla_mc.blocks.slots.handlerslots;

import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilterGroup;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotHandler extends Slot {

    ItemFilterGroup filter;

    public SlotHandler(SimpleInventory itemHandler, int index, int xPosition, int yPosition,
                       ItemFilterGroup filter) {
        super(itemHandler, index, xPosition, yPosition);
        this.filter = filter;

    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return filter.anyMatchesFilter(stack);
    }

}
