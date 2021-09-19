package com.robertx22.age_of_exile.gui.screens.wiki;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class WikiEntry {

    public abstract ItemStack createMainStack();

    public abstract List<ITextComponent> getExtraInfo();

    public abstract String getName();

    public abstract TextFormatting getFormat();

    public String cutName(String str) {
        return StringUtils.abbreviate(str, 23);

    }
}
