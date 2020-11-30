package com.robertx22.age_of_exile.saveclasses.spells.calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.List;

@Storable
public class ScalingStatCalc extends BaseStatCalc {

    @Store
    public String statID;

    @Store
    public float multi;

    public Stat getStat() {
        return Database.Stats()
            .get(statID);
    }

    @Factory
    public ScalingStatCalc() {

    }

    public ScalingStatCalc(Stat stat, float multi) {
        super();
        this.statID = stat.GUID();
        this.multi = multi;
    }

    @Override
    public float getMulti() {
        return multi;
    }

    public int getMultiAsPercent() {
        return (int) (multi * 100);
    }

    @Override
    public int getCalculatedValue(EntityCap.UnitData data) {
        return (int) (data.getUnit()
            .getCalculatedStat(statID)
            .getAverageValue() * multi);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        return getTooltipFor(multi, getCalculatedValue(info.unitdata), getStat().locName(), getStat().getElement());
    }
}
