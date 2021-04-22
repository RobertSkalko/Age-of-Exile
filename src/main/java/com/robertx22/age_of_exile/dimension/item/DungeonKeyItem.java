package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class DungeonKeyItem extends AutoItem {

    public DungeonKeyItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(1));
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

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TooltipUtils.tier(getTier(stack)));
    }

}
