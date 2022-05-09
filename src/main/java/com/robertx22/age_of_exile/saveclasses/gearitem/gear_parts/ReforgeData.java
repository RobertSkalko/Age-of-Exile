package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.reforge.Reforge;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.IStatContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatsWithContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ReforgeData implements IStatContainer {

    @Store
    public String rarity = IRarity.COMMON_ID;
    @Store
    public String reforge = "";

    public boolean hasReforge() {
        return ExileDB.Reforges()
            .isRegistered(reforge);
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rarity);
    }

    public Reforge getReforge() {
        return ExileDB.Reforges()
            .get(reforge);
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<ITextComponent> list = new ArrayList<>();

        //list.add(new StringTextComponent(" "));

        list.add(reforgeStars());

        for (StatModifierInfo stat : getStatsWithContext(gear).list) {
            list.addAll(stat.GetTooltipString(info));
        }

        //list.add(new StringTextComponent(" "));

        return list;

    }

    public IFormattableTextComponent reforgeStars() {
        int stars = getRarity().item_tier;

        IFormattableTextComponent starstext = new StringTextComponent("");

        IFormattableTextComponent txt = new StringTextComponent("").append(getReforge()
                .locName())
            .append(":");

        for (int i = 0; i < stars; i++) {
            starstext = starstext.append(getRarity()
                .textFormatting() + TooltipUtils.STAR);
        }
        if (stars > 0) {
            txt.append(" (");
            txt.append(starstext);
            txt.append(")");
        }

        return txt.withStyle(TextFormatting.GREEN);
    }

    @Override
    public StatsWithContext getStatsWithContext(GearItemData gear) {
        List<StatModifierInfo> list = new ArrayList<>();

        try {
            for (StatModifier stat : getReforge().stats) {
                list.add(new StatModifierInfo(stat,
                    new StatPercent(this.getRarity().reforge_stat_percent),
                    gear.getLevel()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new StatsWithContext(list);
    }
}

