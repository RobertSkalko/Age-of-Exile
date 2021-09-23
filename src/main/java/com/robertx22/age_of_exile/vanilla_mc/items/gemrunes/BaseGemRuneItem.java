package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGemRuneItem extends Item {
    public BaseGemRuneItem(Properties settings) {
        super(settings);
    }

    public int weight;

    public abstract float getStatValueMulti();

    public abstract BaseRuneGem getBaseRuneGem();

    public abstract List<StatModifier> getStatModsForSerialization(SlotFamily family);

    public List<StatModifier> getStatsForSerialization(SlotFamily family) {

        List<StatModifier> list = new ArrayList<>();

        float multi = getStatValueMulti();

        this.getStatModsForSerialization(family)
            .forEach(x -> {

                list.add(new StatModifier(
                    x.min * multi, x.max * multi,
                    x.GetStat(), x.getModType()));

            });

        return list;

    }

    public List<ITextComponent> getBaseTooltip() {
        List<ITextComponent> tooltip = new ArrayList<>();

        if (ExileDB.Runes()
            .isEmpty() || ExileDB.Gems()
            .isEmpty() || getBaseRuneGem() == null) {
            return tooltip; // datapacks didnt register yet
        }

        BaseRuneGem gem = getBaseRuneGem();

        int efflvl = Load.Unit(ClientOnly.getPlayer())
            .getLevel();

        tooltip.add(new StringTextComponent(""));
        List<StatModifier> wep = gem.getFor(SlotFamily.Weapon);
        tooltip.add(new StringTextComponent("On Weapon:").withStyle(TextFormatting.RED));
        for (StatModifier x : wep) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new StringTextComponent(""));
        List<StatModifier> armor = gem.getFor(SlotFamily.Armor);
        tooltip.add(new StringTextComponent("On Armor:").withStyle(TextFormatting.BLUE));
        for (StatModifier x : armor) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new StringTextComponent(""));
        List<StatModifier> jewelry = gem.getFor(SlotFamily.Jewelry);
        tooltip.add(new StringTextComponent("On Jewelry:").withStyle(TextFormatting.LIGHT_PURPLE));
        for (StatModifier x : jewelry) {
            tooltip.addAll(x.getEstimationTooltip(efflvl));
        }

        tooltip.add(new StringTextComponent(""));
        tooltip.add(TooltipUtils.level(gem.getReqLevel()));

        return tooltip;
    }

}
