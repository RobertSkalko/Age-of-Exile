package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
    public List<Text> getExtraInfo() {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Set Pieces: ").formatted(Formatting.GREEN));
        Database.UniqueGears()
            .getList()
            .forEach(x -> {
                if (x.getSet()
                    .equals(set)) {
                    list.add(x.locName()
                        .append(" ")
                        .append(x.getBaseGearType()
                            .locName()));
                }
            });

        list.add(new LiteralText(""));

        TooltipInfo info = new TooltipInfo();

        list.addAll(set.GetTooltipString(info));

        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(set.locName());
    }

    @Override
    public Formatting getFormat() {
        return Formatting.GREEN;
    }
}
