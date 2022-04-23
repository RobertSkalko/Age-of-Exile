package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class ScalingCalc {

    public String stat;
    public float multi;

    public Stat getStat() {
        return ExileDB.Stats()
            .get(stat);
    }

    @Factory
    public ScalingCalc() {

    }

    public ScalingCalc(Stat stat, float multi) {
        super();
        this.stat = stat.GUID();
        this.multi = multi;
    }

    public float getMulti() {
        return multi;
    }

    public int getMultiAsPercent() {
        return (int) (multi * 100);
    }

    public ITextComponent GetTooltipString() {
        return new StringTextComponent("(" + getMultiAsPercent() + "% of " + getStat().getIconNameFormat() + ")");
    }

    public List<ITextComponent> getTooltipFor(float multi, float value, IFormattableTextComponent statname, Elements el) {
        List<ITextComponent> list = new ArrayList<>();
        String eleStr = "";

        if (el != null) {
            eleStr = el.format + el.icon;
        }

        if (statname != null) {
            list.add(new StringTextComponent(
                TextFormatting.RED + "Scales with " + (int) (multi * 100F) + "% " + eleStr + " ").append(
                    statname)
                .append(" (" + value + ")"));
        }

        return list;
    }

    public int getCalculatedValue(EntityData data) {

        return (int) (getMulti() * data
            .getUnit()
            .getCalculatedStat(stat)
            .getValue());

    }
}
