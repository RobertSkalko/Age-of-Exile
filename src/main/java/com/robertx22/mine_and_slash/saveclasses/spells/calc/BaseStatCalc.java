package com.robertx22.mine_and_slash.saveclasses.spells.calc;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Storable;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Storable
public abstract class BaseStatCalc implements ITooltipList {

    public List<Text> getTooltipFor(float multi, float value, Text statname, Elements el) {
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
