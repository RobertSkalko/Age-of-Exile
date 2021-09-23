package com.robertx22.age_of_exile.player_skills.ingredient.data;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class CraftedConsumableData {

    @Store
    public int seconds = 60;
    @Store
    public String crafter = "";

    @Store
    public String prof = "";
    @Store
    public int tier = 1;
    @Store
    public int uses = 1;
    @Store
    public int maxuses = 1;
    @Store
    public List<ExactStatData> stats = new ArrayList<>();

    public boolean canEat(PlayerEntity player) {

        int lvl = LevelUtils.tierToLevel(tier);

        return Load.Unit(player)
            .getLevel() >= lvl;
    }

    public void makeTooltip(ItemStack stack, List<ITextComponent> tip) {

        tip.clear();

        IFormattableTextComponent name = new StringTextComponent("").append(stack.getDisplayName())
            .withStyle(TextFormatting.DARK_AQUA);

        if (maxuses > 1) {
            name.append(new StringTextComponent(" [" + uses + "/" + maxuses + "]").withStyle(TextFormatting.AQUA));
        }

        tip.add(name);
        if (!crafter.isEmpty()) {
            tip.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Crafted by: " + TextFormatting.LIGHT_PURPLE + crafter));
        }

        tip.add(new StringTextComponent(""));

        MergedStats merged = new MergedStats(stats, new TooltipInfo());

        tip.add(Words.Stats.locName()
            .withStyle(TextFormatting.AQUA)
            .append(":"));
        tip.addAll(merged.GetTooltipString(new TooltipInfo(), null));

        tip.add(new StringTextComponent(""));

        tip.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Duration: " + TextFormatting.BLUE + seconds + "s"));

        tip.add(new StringTextComponent(""));

        tip.add(TooltipUtils.gearLevel(LevelUtils.tierToLevel(tier)));
        tip.add(TooltipUtils.gearTier(tier));

    }
}
