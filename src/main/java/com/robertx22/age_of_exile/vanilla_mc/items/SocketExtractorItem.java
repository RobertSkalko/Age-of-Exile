package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SocketExtractorItem extends AutoItem {

    public SocketExtractorItem() {
        super(new Item.Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Gem Socket Extractor Tool";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new StringTextComponent("Click on gear to extract a gem.").withStyle(TextFormatting.RED));
    }
}
