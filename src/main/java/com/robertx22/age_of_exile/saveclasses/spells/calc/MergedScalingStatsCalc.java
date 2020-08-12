package com.robertx22.age_of_exile.saveclasses.spells.calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Collectors;

@Storable
public class MergedScalingStatsCalc extends BaseStatCalc {

    @Store
    public List<String> statIDs;

    @Store
    public float multi;

    MutableText name;

    @Factory
    private MergedScalingStatsCalc() {

    }

    public MergedScalingStatsCalc(List<Stat> stats, float multi, MutableText name) {
        super();
        this.statIDs = stats.stream()
            .map(x -> x.GUID())
            .collect(Collectors.toList());
        this.multi = multi;
        this.name = name;
    }

    @Override
    public float getMulti() {
        return multi;
    }

    @Override
    public int getCalculatedValue(EntityCap.UnitData data) {

        float val = 0;

        for (String x : statIDs) {
            val += data.getUnit()
                .peekAtStat(x)
                .getAverageValue() * multi;
        }

        return (int) val;

    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        return getTooltipFor(multi, getCalculatedValue(info.unitdata), name, Elements.Elemental);
    }
}
