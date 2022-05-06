package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.RareItemAffixNames;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.IStatContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatsWithContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class BaseStatsData implements IRerollable, IGearPartTooltip, IStatContainer {

    @Store
    public Integer perc = 50;

    @Override
    public void RerollFully(GearItemData gear) {
        perc = getMinMax(gear).random();

        gear.rp = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.prefixAny
            .keySet()));
        gear.rs = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.getSuffixMap(gear.GetBaseGearType())
            .keySet()));

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent(" "));

        for (StatModifierInfo stat : getStatsWithContext(gear).list) {
            list.addAll(stat.GetTooltipString(info));
        }

        return list;

    }

    @Override
    public Part getPart() {
        return Part.BASE_STATS;
    }

    @Override
    public StatsWithContext getStatsWithContext(GearItemData gear) {
        List<StatModifierInfo> list = new ArrayList<>();

        try {

            if (!gear.uniqueBaseStatsReplaceBaseStats()) {
                for (StatModifier mod : gear.GetBaseGearType()
                    .baseStats()) {
                    list.add(new StatModifierInfo(mod, new StatPercent(perc), gear.getLevel()));

                }
            } else {
                for (StatModifier mod : gear.uniqueStats.getUnique(gear)
                    .base_stats) {
                    list.add(new StatModifierInfo(mod, new StatPercent(perc), gear.getLevel()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new StatsWithContext(list);
    }
}
