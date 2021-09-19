package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import net.minecraft.item.Item.Properties;

public class ItemDefault extends Properties {

    public ItemDefault() {
        this.stacksTo(64);
        // this.defaultMaxDamage(0); max dmg sets stakc size to 1!!!
        this.tab(CreativeTabs.MyModTab);

    }
}
