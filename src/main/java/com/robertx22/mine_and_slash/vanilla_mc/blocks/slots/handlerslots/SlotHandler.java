package com.robertx22.mine_and_slash.vanilla_mc.blocks.slots.handlerslots;

import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import net.minecraft.container.Slot;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;

public class SlotHandler extends Slot {

    ItemFilterGroup filter;

    public SlotHandler(BasicInventory itemHandler, int index, int xPosition, int yPosition,
                       ItemFilterGroup filter) {
        super(itemHandler, index, xPosition, yPosition);
        this.filter = filter;

    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return filter.anyMatchesFilter(stack);
    }

}
