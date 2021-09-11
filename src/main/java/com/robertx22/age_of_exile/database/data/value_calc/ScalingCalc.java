package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class ScalingCalc {

    public String stat;
    public LeveledValue multi;

    public Stat getStat() {
        return ExileDB.Stats()
            .get(stat);
    }

    @Factory
    public ScalingCalc() {

    }

    public ScalingCalc(Stat stat, LeveledValue multi) {
        super();
        this.stat = stat.GUID();
        this.multi = multi;
    }

    public LeveledValue getMulti() {
        return multi;
    }

    public int getMultiAsPercent() {
        return (int) (multi * 100);
    }

    public Text GetTooltipString() {
        return new LiteralText(getMultiAsPercent() + "% " + getStat().getIconNameFormat());
    }
}
