package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneWordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class RuneWordEntry extends WikiEntry {

    RuneWord runeword;

    public RuneWordEntry(RuneWord runeword) {
        this.runeword = runeword;
    }

    @Override
    public ItemStack createMainStack() {
        return RuneWordItem.createStack(runeword);
    }

    @Override
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();
        return list;
    }

    @Override
    public String getName() {
        return runeword.translate();
    }

    @Override
    public Formatting getFormat() {
        return Formatting.GOLD;
    }
}
