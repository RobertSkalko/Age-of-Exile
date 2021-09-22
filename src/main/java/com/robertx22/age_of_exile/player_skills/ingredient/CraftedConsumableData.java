package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class CraftedConsumableData {

    @Store
    public int seconds = 60; // todo

    @Store
    public String prof = "";
    @Store
    public int uses = 1;
    @Store
    public int maxuses = 1;
    @Store
    public List<ExactStatData> stats = new ArrayList<>();

    public void makeTooltip(List<ITextComponent> tip) {

        tip.clear();

        IFormattableTextComponent name = new StringTextComponent("Crafted Food").withStyle(TextFormatting.DARK_AQUA);
        if (maxuses > 1) {
            name.append(new StringTextComponent(" [" + uses + "/" + maxuses + "]").withStyle(TextFormatting.AQUA));
        }

        tip.add(name);

        tip.add(new StringTextComponent(""));

        MergedStats merged = new MergedStats(stats, new TooltipInfo());

        tip.addAll(merged.GetTooltipString(new TooltipInfo(), null));

        tip.add(new StringTextComponent(""));

        tip.add(new StringTextComponent("Duration: " + seconds + "s").withStyle(TextFormatting.BLUE));

    }
}
