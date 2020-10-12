package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class UniqueStatsData implements IGearPartTooltip, IRerollable, IStatsContainer {

    public UniqueStatsData() {

    }

    @Store
    public List<Integer> percents = new ArrayList<Integer>();

    @Override
    public void RerollFully(GearItemData gear) {
        this.RerollNumbers(gear);
    }

    @Override
    public void RerollNumbers(GearItemData gear) {

        percents.clear();

        // wont ever have more than 10 unique stats.
        for (int i = 0; i < 10; i++) {
            percents.add(getMinMax(gear)
                .random());
        }

    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {

        info.minmax = getMinMax(gear);

        List<Text> list = new ArrayList<Text>();

        list.add(new SText(""));

        GetAllStats(gear).forEach(x -> list.addAll(x.GetTooltipString(info)));

        return list;

    }

    public UniqueGear getUnique(GearItemData gear) {
        return SlashRegistry.UniqueGears()
            .get(gear.unique_id);

    }

    @Override
    public Part getPart() {
        return Part.UNIQUE_STATS;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        List<ExactStatData> list = new ArrayList<>();

        int i = 0;
        for (StatModifier mod : SlashRegistry.UniqueGears()
            .get(gear.unique_id)
            .uniqueStats()) {
            list.add(mod.ToExactStat(percents.get(i), gear.level));
            i++;
        }
        return list;

    }
}
