package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.database.data.rarities.gears.UniqueGear;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemSword;

public final class BaseUniqueSword extends ItemSword {
    public BaseUniqueSword() {
        super(UniqueGear.getInstance()
            .Rank());
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
