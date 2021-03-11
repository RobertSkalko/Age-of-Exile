package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("Mob levels: " + dim.min_lvl + "-" + dim.max_lvl));
        return list;
    }

    @Override
    public String getName() {
        return dim.dimension_id;
    }

    @Override
    public Formatting getFormat() {
        return Formatting.AQUA;
    }
}
