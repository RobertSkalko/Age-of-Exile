package com.robertx22.age_of_exile.database.data.spell_modifiers;

import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SpellModStatData implements ITooltipList, IByteBuf<SpellModStatData> {
    public static SpellModStatData SERIALIZER = new SpellModStatData();

    public float value;
    public SpellModEnum spell_stat;

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
        String plus = value > 0 ? "+" : "";
        MutableText txt = new LiteralText(plus + value + perc + " ").append(spell_stat.word.locName());
        list.add(txt);
        return list;
    }

    @Override
    public SpellModStatData getFromBuf(PacketByteBuf buf) {
        SpellModStatData data = new SpellModStatData();
        data.spell_stat = SpellModEnum.valueOf(buf.readString(100));
        data.value = buf.readFloat();
        return data;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(spell_stat.name());
        buf.writeFloat(value);
    }
}
