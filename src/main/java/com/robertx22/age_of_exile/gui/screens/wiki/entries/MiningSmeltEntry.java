package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class MiningSmeltEntry extends WikiEntry {

    Item item;
    int exp;

    public MiningSmeltEntry(Item item, int exp) {
        this.item = item;
        this.exp = exp;
    }

    @Override
    public ItemStack createMainStack() {
        ItemStack stack = new ItemStack(item);
        return stack;
    }

    @Override
    public List<ITextComponent> getExtraInfo() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent("Exp Given for smelting " + exp));
        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(item.getDescription()) + ", " + exp + " Exp";
    }

    @Override
    public TextFormatting getFormat() {
        return TextFormatting.RED;
    }
}
