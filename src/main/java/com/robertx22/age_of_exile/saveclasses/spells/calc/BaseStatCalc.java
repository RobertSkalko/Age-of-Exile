package com.robertx22.age_of_exile.saveclasses.spells.calc;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Storable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public abstract class BaseStatCalc {

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

    public abstract float getMulti();

    public abstract int getCalculatedValue(EntityCap.UnitData data);
}
