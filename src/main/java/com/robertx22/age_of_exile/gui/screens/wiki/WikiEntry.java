package com.robertx22.age_of_exile.gui.screens.wiki;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public abstract class WikiEntry {

    public abstract ItemStack createMainStack();

    public abstract List<Text> getExtraInfo();

    public abstract String getName();

    public abstract Formatting getFormat();

}
