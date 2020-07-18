package com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.slots.handlerslots.CurrencySlot;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseContainer;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseInventory;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseSlot;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModContainers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;

public class ContainerCurrencyBag extends BaseContainer {

    public ContainerCurrencyBag(int i, PlayerInventory playerInventory) {

        this(i, playerInventory, new BaseInventory(new ItemStack(ItemCurrencyBag.ITEM)));

    }

    public ContainerCurrencyBag(int i, PlayerInventory playerInv, BaseInventory basebag) {
        super(ModContainers.CURRENCY_BAG, i, playerInv, basebag);
    }

    @Override
    public BaseSlot slot(BasicInventory inv, int index, int x, int y) {
        return new CurrencySlot(inv, index, x, y);
    }

}