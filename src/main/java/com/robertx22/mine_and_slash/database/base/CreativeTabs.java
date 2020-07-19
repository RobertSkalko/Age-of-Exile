package com.robertx22.mine_and_slash.database.base;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemSword;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTabs {

    public static final ItemGroup MyModTab = new ItemGroup(10, Ref.MODID + "_main") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemSword.Items.get(2));
        }

    };

}
