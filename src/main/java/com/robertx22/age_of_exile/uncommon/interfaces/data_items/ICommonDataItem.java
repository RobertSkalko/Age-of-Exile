package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.item.ItemStack;

public interface ICommonDataItem<R extends Rarity> extends ITiered, ISalvagable, ITooltip, IRarity {

    @Override
    default boolean isSalvagable(SalvageContext context) {
        return true;
    }

    ItemstackDataSaver<? extends ICommonDataItem> getStackSaver();

    void saveToStack(ItemStack stack);

    static ICommonDataItem load(ItemStack stack) {

        for (ItemstackDataSaver<? extends ICommonDataItem> saver : StackSaving.ALL) {
            ICommonDataItem data = saver.loadFrom(stack);
            if (data != null) {
                return data;
            }
        }
        return null;
    }

}
