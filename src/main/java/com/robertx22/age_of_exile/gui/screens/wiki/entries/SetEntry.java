package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class SetEntry extends WikiEntry {

    GearSet set;

    public SetEntry(GearSet set) {
        this.set = set;
    }

    @Override
    public ItemStack createMainStack() {
        return new ItemStack(Items.PAPER);
    }

    @Override
    public List<ITextComponent> getExtraInfo() {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Set Pieces: ").withStyle(TextFormatting.GREEN));
        ExileDB.UniqueGears()
            .getList()
            .forEach(x -> {
                if (x.getSet()
                    .equals(set)) {
                    list.add(x.locName()
                        .append(" ")
                        .append(x.getBaseGear()
                            .locName()));
                }
            });

        list.add(new StringTextComponent(""));

        TooltipInfo info = new TooltipInfo();

        list.addAll(set.GetTooltipString(info));

        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(set.locName());
    }

    @Override
    public TextFormatting getFormat() {
        return TextFormatting.GREEN;
    }
}
