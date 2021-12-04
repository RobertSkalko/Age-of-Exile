package com.robertx22.addon.infinite_dungeons;

import com.robertx22.addon.infinite_dungeons.data_gen.IDAddonShopListsAdder;
import com.robertx22.age_of_exile.mmorpg.MMORPG;

public class IDAddonRegInit {

    public static void init() {

        if (MMORPG.RUN_DEV_TOOLS) {
            new IDAddonShopListsAdder().registerAll();
        }
    }
}
