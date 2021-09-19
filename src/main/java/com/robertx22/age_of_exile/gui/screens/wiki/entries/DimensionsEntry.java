package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DimensionsEntry extends WikiEntry {

    DimensionConfig dim;

    public DimensionsEntry(DimensionConfig dim) {
        this.dim = dim;
    }

    @Override
    public ItemStack createMainStack() {
        ItemStack stack = new ItemStack(Items.MAP);
        return stack;
    }

    @Override
    public List<ITextComponent> getExtraInfo() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent("Mob levels: " + dim.min_lvl + "-" + dim.max_lvl));
        return list;
    }

    @Override
    public String getName() {
        return dim.dimension_id;
    }

    @Override
    public TextFormatting getFormat() {
        return TextFormatting.AQUA;
    }
}
