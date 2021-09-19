package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class MiningBlockExpEntry extends WikiEntry {

    Block block;
    int exp;

    public MiningBlockExpEntry(Block block, int exp) {
        this.block = block;
        this.exp = exp;
    }

    @Override
    public ItemStack createMainStack() {
        ItemStack stack = new ItemStack(block);
        return stack;
    }

    @Override
    public List<ITextComponent> getExtraInfo() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent("Exp Given " + exp));
        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(block.getName()) + ", " + exp + " Exp";
    }

    @Override
    public TextFormatting getFormat() {
        return TextFormatting.YELLOW;
    }
}
