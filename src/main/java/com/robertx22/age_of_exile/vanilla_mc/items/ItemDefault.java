package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import net.minecraft.item.Item.Settings;

public class ItemDefault extends Settings {

    public ItemDefault() {
        this.maxCount(64);
        // this.defaultMaxDamage(0); max dmg sets stakc size to 1!!!
        this.group(CreativeTabs.MyModTab);

    }
}
