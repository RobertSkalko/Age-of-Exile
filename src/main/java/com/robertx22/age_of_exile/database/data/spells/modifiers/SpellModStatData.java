package com.robertx22.age_of_exile.database.data.spells.modifiers;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SpellModStatData implements ITooltipList {

    public float value;
    public SpellModEnum spell_stat;
    // public SpellModType mod_type = SpellModType.PERCENT;

    public static SpellModStatData create(SpellModEnum spellStat) {
        SpellModStatData data = new SpellModStatData();
        data.value = spellStat.defaultValue;
        data.spell_stat = spellStat;
        return data;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        String perc = "%";
        MutableText txt = new LiteralText(value + perc + " ").append(spell_stat.word.locName());
        list.add(txt);
        return list;
    }
}
