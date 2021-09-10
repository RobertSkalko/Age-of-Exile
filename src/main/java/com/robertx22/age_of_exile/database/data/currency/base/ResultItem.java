package com.robertx22.age_of_exile.database.data.currency.base;

import net.minecraft.item.ItemStack;

public class ResultItem {

    public ItemStack stack;
    public ModifyResult resultEnum;

    public ResultItem(ItemStack stack, ModifyResult resultEnum) {
        this.stack = stack;
        this.resultEnum = resultEnum;
    }
}
