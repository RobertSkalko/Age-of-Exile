package com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag;

import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseBagItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;

public class ItemCurrencyBag extends BaseBagItem {

    public static final String ID = "currency_bag";

    public ItemCurrencyBag() {
        super(ID);
    }

    @Override
    public ItemFilterGroup filterGroup() {
        return ItemFilterGroup.CURRENCY_BAG;
    }

    @Override
    public NamedScreenHandlerFactory getNamedContainer(ItemStack stack) {
        return new NamedContainerCurrencyBag(stack);
    }

}
