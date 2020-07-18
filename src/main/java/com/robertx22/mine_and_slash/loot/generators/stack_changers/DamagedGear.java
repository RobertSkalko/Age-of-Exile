package com.robertx22.mine_and_slash.loot.generators.stack_changers;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.loot.LootUtils;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.item.ItemStack;

public class DamagedGear implements IStackAction {

    private DamagedGear() {

    }

    public static DamagedGear INSTANCE = new DamagedGear();

    @Override
    public void changeStack(ItemStack stack) {

        ICommonDataItem data = ICommonDataItem.load(stack);

        if (data != null) {
            if (data.getRarity() instanceof GearRarity) {
                LootUtils.RandomDamagedGear(stack, (GearRarity) data.getRarity());
            } else {
                System.out.println("trying to damage gear that isn't gear rarity");
            }
        }

    }
}
