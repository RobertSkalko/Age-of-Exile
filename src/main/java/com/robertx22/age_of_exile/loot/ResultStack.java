package com.robertx22.age_of_exile.loot;

import net.minecraft.item.ItemStack;

public class ResultStack {

    public ItemStack stack = ItemStack.EMPTY;
    public float favor_cost = 1;

    public ResultStack(ItemStack stack, float favor_cost) {
        this.stack = stack;
        this.favor_cost = favor_cost;
    }
}
