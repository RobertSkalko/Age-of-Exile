package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import net.minecraft.item.Item;

public class SimpleMatItem extends Item {

    public SimpleMatItem() {
        super(new Item.Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));

    }
}

