package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class DungeonKeyItem extends AutoItem {

    public DungeonKeyItem() {
        super(new Settings().group(CreativeTabs.MyModTab));
    }

    @Override
    public String locNameForLangFile() {
        return "Dungeon Key";
    }

    @Override
    public String GUID() {
        return "";
    }

    public static void setTier(ItemStack stack, int tier) {

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag()
            .putInt("tier", tier);
    }

    public static int getTier(ItemStack stack) {
        return stack.hasTag() ? stack.getTag()
            .getInt("tier") : 0;
    }

}
