package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SalvageHammerItem extends AutoItem {

    public SalvageHammerItem() {
        super(new Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Salvage Hammer";
    }

    @Override
    public String GUID() {
        return "salvage_hammer";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new StringTextComponent("Click on items to salvage them.").withStyle(TextFormatting.RED));
    }
}
