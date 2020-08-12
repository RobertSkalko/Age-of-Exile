package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTabs {

    public static final ItemGroup MyModTab = new ItemGroup(10, Ref.MODID + "_main") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(BaseGearTypes.END_SWORD.getItem());
        }

    };

}
