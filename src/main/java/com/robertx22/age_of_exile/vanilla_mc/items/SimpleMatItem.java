package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import net.minecraft.item.Item;

public class SimpleMatItem extends Item {

    public SimpleMatItem() {
        super(new Item.Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(64));

    }
}

