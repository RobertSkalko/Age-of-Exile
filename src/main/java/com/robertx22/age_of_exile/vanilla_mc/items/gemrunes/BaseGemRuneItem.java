package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
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

    public abstract BaseRuneGem getBaseRuneGem();

    public abstract float getStatValueMulti();

    public abstract List<StatModifier> getStatModsForSerialization(SlotFamily family);

    public List<OptScaleExactStat> getStatsForSerialization(SlotFamily family) {

        List<OptScaleExactStat> list = new ArrayList<>();

        float multi = getStatValueMulti();

        this.getStatModsForSerialization(family)
            .forEach(x -> {
                OptScaleExactStat stat = new OptScaleExactStat(x.max * multi, x.GetStat(), x.getModType());
                stat.scale_to_lvl = true;
                list.add(stat);

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

        TooltipInfo info = new TooltipInfo();

        tooltip.add(new StringTextComponent(""));
        List<OptScaleExactStat> wep = gem.getFor(SlotFamily.Weapon);
        tooltip.add(new StringTextComponent("On Weapon:").withStyle(TextFormatting.RED));
        for (OptScaleExactStat x : wep) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new StringTextComponent(""));
        List<OptScaleExactStat> armor = gem.getFor(SlotFamily.Armor);
        tooltip.add(new StringTextComponent("On Armor:").withStyle(TextFormatting.BLUE));
        for (OptScaleExactStat x : armor) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new StringTextComponent(""));
        List<OptScaleExactStat> jewelry = gem.getFor(SlotFamily.Jewelry);
        tooltip.add(new StringTextComponent("On Jewelry:").withStyle(TextFormatting.LIGHT_PURPLE));
        for (OptScaleExactStat x : jewelry) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new StringTextComponent(""));
        tooltip.add(TooltipUtils.tier(gem.tier));

        return tooltip;
    }

}
