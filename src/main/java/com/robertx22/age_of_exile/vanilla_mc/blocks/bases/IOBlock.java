package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IOBlock {

    boolean isItemValidInput(ItemStack stack);

    boolean isAutomatable();

    boolean isOutputSlot(int slot);

    List<Integer> inputSlots();

}
