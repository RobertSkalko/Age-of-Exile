package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.database.data.rarities.gears.UniqueGear;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;

public final class BaseUniquePantsItem extends PlatePantsItem {

    public BaseUniquePantsItem() {
        super(UniqueGear.getInstance()
            .Rank());

    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
