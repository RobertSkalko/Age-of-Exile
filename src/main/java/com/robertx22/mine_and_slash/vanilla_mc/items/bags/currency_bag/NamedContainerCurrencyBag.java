package com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag;

import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseInventory;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class NamedContainerCurrencyBag implements NameableContainerFactory {

    private final ItemStack stack;

    public NamedContainerCurrencyBag(ItemStack stack) {
        this.stack = stack;
    }


    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerCurrencyBag(i, inventory, new BaseInventory(stack));
    }

    @Override
    public MutableText getDisplayName() {
        return new LiteralText("Currency Bag");
    }

}
