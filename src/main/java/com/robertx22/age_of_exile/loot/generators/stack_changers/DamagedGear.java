package com.robertx22.age_of_exile.loot.generators.stack_changers;

import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.item.ItemStack;

public class DamagedGear implements IStackAction {

    private DamagedGear() {

    }

    public static DamagedGear INSTANCE = new DamagedGear();

    @Override
    public void changeStack(ItemStack stack) {

        GearItemData data = Gear.Load(stack);

        if (data != null) {
            LootUtils.RandomDamagedGear(stack, data.getRarity());
        }

    }
}
