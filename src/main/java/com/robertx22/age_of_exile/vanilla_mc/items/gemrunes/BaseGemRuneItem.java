package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGemRuneItem extends Item {
    public BaseGemRuneItem(Settings settings) {
        super(settings);
    }

    public int weight;

    public abstract float getStatMultiForNonLvlScaledStat();

    public abstract BaseRuneGem getBaseRuneGem();

    public abstract List<StatModifier> getStatModsForSerialization(SlotFamily family);

    public List<StatModifier> getStatsForSerialization(SlotFamily family) {

        List<StatModifier> list = new ArrayList<>();

        float multi = getStatMultiForNonLvlScaledStat();

        this.getStatModsForSerialization(family)
            .forEach(x -> {
                if (x.GetStat()
                    .getScaling() == StatScaling.NORMAL) {
                    list.add(x);
                } else {
                    if (x.GetStat()
                        .UsesSecondValue()) {
                        list.add(new StatModifier(
                            x.min1 * multi, x.max1 * multi, x.min2 * multi, x.max2 * multi,
                            x.GetStat(), x.getModType()));
                    } else {
                        list.add(new StatModifier(
                            x.min1 * multi, x.max1 * multi,
                            x.GetStat(), x.getModType()));
                    }

                }
            });

        return list;

    }

    public List<Text> getBaseTooltip() {
        List<Text> tooltip = new ArrayList<>();

        if (Database.Runes()
            .isEmpty() || Database.Gems()
            .isEmpty() || getBaseRuneGem() == null) {
            return tooltip; // datapacks didnt register yet
        }

        BaseRuneGem gem = getBaseRuneGem();

        int efflvl = gem.getEffectiveLevel();

        tooltip.add(new LiteralText(""));
        List<StatModifier> wep = gem.getFor(SlotFamily.Weapon);
        tooltip.add(new LiteralText("On Weapon:").formatted(Formatting.RED));
        for (StatModifier x : wep) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new LiteralText(""));
        List<StatModifier> armor = gem.getFor(SlotFamily.Armor);
        tooltip.add(new LiteralText("On Armor:").formatted(Formatting.BLUE));
        for (StatModifier x : armor) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new LiteralText(""));
        List<StatModifier> jewelry = gem.getFor(SlotFamily.Jewelry);
        tooltip.add(new LiteralText("On Jewelry:").formatted(Formatting.LIGHT_PURPLE));
        for (StatModifier x : jewelry) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new LiteralText(""));
        tooltip.add(TooltipUtils.level(gem.getReqLevel()));

        return tooltip;
    }

}
