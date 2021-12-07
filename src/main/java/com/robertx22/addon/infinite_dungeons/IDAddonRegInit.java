package com.robertx22.addon.infinite_dungeons;

import com.robertx22.addon.infinite_dungeons.data_gen.IDAddonRewardListAdder;
import com.robertx22.addon.infinite_dungeons.data_gen.IDAddonShopListsAdder;
import com.robertx22.addon.infinite_dungeons.events.IDAddonEventRegister;
import com.robertx22.age_of_exile.mmorpg.MMORPG;

public class IDAddonRegInit {

    public static void init() {

        IDAddonEventRegister.register();

        if (MMORPG.RUN_DEV_TOOLS) {
            new IDAddonRewardListAdder().registerAll();
            new IDAddonShopListsAdder().registerAll();
        }
    }
}
