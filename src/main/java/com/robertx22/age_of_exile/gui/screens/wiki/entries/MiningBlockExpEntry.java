package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("Exp Given " + exp));
        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(block.getName()) + ", " + exp + " Exp";
    }

    @Override
    public Formatting getFormat() {
        return Formatting.YELLOW;
    }
}
