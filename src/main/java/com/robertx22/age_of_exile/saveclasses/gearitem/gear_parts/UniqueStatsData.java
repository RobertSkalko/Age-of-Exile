package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.IStatContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatsWithContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class UniqueStatsData implements IGearPartTooltip, IRerollable, IStatContainer {

    public UniqueStatsData() {

    }

    @Store
    public List<Integer> perc = new ArrayList<Integer>();

    @Override
    public void RerollFully(GearItemData gear) {
        this.RerollNumbers(gear);
    }

    public static int MAX_STATS = 12;

    @Override
    public void RerollNumbers(GearItemData gear) {
        perc.clear();
        // wont ever have more than 12 unique stats.
        for (int i = 0; i < MAX_STATS; i++) {
            perc.add(getMinMax(gear)
                .random());
        }
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {

        info.minmax = getMinMax(gear);

        List<ITextComponent> list = new ArrayList<ITextComponent>();

        for (StatModifierInfo stat : getStatsWithContext(gear).list) {
            list.addAll(stat.GetTooltipString(info));
        }

        return list;

    }

    public UniqueGear getUnique(GearItemData gear) {
        return ExileDB.UniqueGears()
            .get(gear.uniq_id);

    }

    @Override
    public Part getPart() {
        return Part.UNIQUE_STATS;
    }

    @Override
    public StatsWithContext getStatsWithContext(GearItemData gear) {
        List<StatModifierInfo> list = new ArrayList<>();

        try {
            int i = 0;
            for (StatModifier mod : ExileDB.UniqueGears()
                .get(gear.uniq_id)
                .uniqueStats()) {
                list.add(new StatModifierInfo(mod, new StatPercent(perc.get(i)), gear.getLevel()));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new StatsWithContext(list);
    }
}
