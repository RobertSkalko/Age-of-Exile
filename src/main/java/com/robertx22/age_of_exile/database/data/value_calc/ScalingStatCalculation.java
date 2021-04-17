package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.spells.calc.BaseStatCalc;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Storable
public class ScalingStatCalculation extends BaseStatCalc {

    @Store
    public String stat;

    @Store
    public float multi;

    public Stat getStat() {
        return Database.Stats()
            .get(stat);
    }

    @Factory
    public ScalingStatCalculation() {

    }

    public ScalingStatCalculation(Stat stat, float multi) {
        super();
        this.stat = stat.GUID();
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
            .getCalculatedStat(stat)
            .getAverageValue() * multi);
    }

    public Text GetTooltipString() {
        return new LiteralText(getMultiAsPercent() + "% " + getStat().getIconNameFormat());
    }
}
