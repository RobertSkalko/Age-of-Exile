package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import net.minecraft.item.ItemStack;

public interface IOBlock {

    boolean isItemValidInput(ItemStack stack);

    boolean isItemValidOutput(ItemStack stack);

    boolean isAutomatable();

    boolean isOutputSlot(int slot);

    int[] inputSlots();

}
