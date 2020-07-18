package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.database.data.rarities.gears.UniqueGear;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemNecklace;

public final class BaseUniqueNecklace extends ItemNecklace {
    public BaseUniqueNecklace() {
        super(UniqueGear.getInstance()
            .Rank());
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
