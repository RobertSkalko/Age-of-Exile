package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

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

    public int getMultiAsPercent(LevelProvider provider) {
        return (int) (multi.getValue(provider) * 100);
    }

    public Text GetTooltipString(LevelProvider provider) {
        return new LiteralText(getMultiAsPercent(provider) + "% " + getStat().getIconNameFormat());
    }

    public List<Text> getTooltipFor(float multi, float value, MutableText statname, Elements el) {
        List<Text> list = new ArrayList<>();
        String eleStr = "";

        if (el != null) {
            eleStr = el.format + el.icon;
        }

        if (statname != null) {
            list.add(new LiteralText(
                Formatting.RED + "Scales with " + (int) (multi * 100F) + "% " + eleStr + " ").append(
                    statname)
                .append(" (" + value + ")"));
        }

        return list;
    }

    public int getCalculatedValue(LevelProvider provider) {

        return (int) (getMulti().getValue(provider) * provider.getCasterData()
            .getUnit()
            .getCalculatedStat(stat)
            .getValue());

    }
}
