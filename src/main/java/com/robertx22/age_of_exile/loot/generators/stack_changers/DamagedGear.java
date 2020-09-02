package com.robertx22.age_of_exile.loot.generators.stack_changers;

import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.item.ItemStack;

public class DamagedGear implements IStackAction {

    private DamagedGear() {

    }

    public static DamagedGear INSTANCE = new DamagedGear();

    @Override
    public void changeStack(ItemStack stack) {

        ICommonDataItem data = ICommonDataItem.load(stack);

        if (data != null) {
            if (data.getRarity() instanceof IGearRarity) {
                LootUtils.RandomDamagedGear(stack, (IGearRarity) data.getRarity());
            } else {
                System.out.println("trying to damage gear that isn't gear rarity");
            }
        }

    }
}
