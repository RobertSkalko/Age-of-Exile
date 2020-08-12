package com.robertx22.age_of_exile.database.data.currency.loc_reqs;

import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.TileGearModify;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.item.ItemStack;

public class LocReqContext {

    public LocReqContext(ItemStack stack, ItemStack currency, TileGearModify tile) {
        this.stack = stack;
        this.Currency = currency;
        this.tileGearModify = tile;
        this.data = ICommonDataItem.load(stack);

        if (currency.getItem() instanceof ICurrencyItemEffect) {
            effect = (ICurrencyItemEffect) currency.getItem();
        }

    }

    public ItemStack stack;
    public ItemStack Currency;
    public TileGearModify tileGearModify;
    public ICommonDataItem data;
    public ICurrencyItemEffect effect;

    public boolean isValid() {
        return !stack.isEmpty() && !Currency.isEmpty();
    }

    public boolean isGear() {
        return data instanceof GearItemData;
    }

    public boolean hasStack() {
        return stack != null && stack.isEmpty() == false;
    }
}
