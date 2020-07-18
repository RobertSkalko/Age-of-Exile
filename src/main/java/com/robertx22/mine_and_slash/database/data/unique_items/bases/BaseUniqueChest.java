package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.database.data.rarities.gears.UniqueGear;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;

public final class BaseUniqueChest extends PlateChestItem {

    public BaseUniqueChest() {
        super(UniqueGear.getInstance()
            .Rank());

    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
