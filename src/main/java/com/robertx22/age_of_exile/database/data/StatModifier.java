package com.robertx22.age_of_exile.database.data;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class StatModifier implements ISerializable<StatModifier>, IByteBuf<StatModifier> {

    @Store
    public float first_min = 0;
    @Store
    public float first_max = 0;

    @Store
    public float second_min = 0;
    @Store
    public float second_max = 0;

    @Store
    public String stat;
    @Store
    public String type;

    public static StatModifier EMPTY = new StatModifier();

    @Factory
    private StatModifier() {

    }

    @Override
    public StatModifier getFromBuf(PacketByteBuf buf) {
        StatModifier mod = new StatModifier();
        mod.first_min = buf.readFloat();
        mod.first_max = buf.readFloat();
        mod.second_min = buf.readFloat();
        mod.second_max = buf.readFloat();

        mod.stat = buf.readString(30);
        mod.type = buf.readString(30);
        return mod;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeFloat(first_min);
        buf.writeFloat(first_max);
        buf.writeFloat(second_min);
        buf.writeFloat(second_max);

        buf.writeString(stat, 30);
        buf.writeString(type, 30);
    }

    public StatModifier(float firstMin, float firstMax, Stat stat, ModType type) {
        this.first_min = firstMin;
        this.first_max = firstMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, Stat stat) {
        this.first_min = firstMin;
        this.first_max = firstMax;
        this.stat = stat.GUID();
        this.type = ModType.FLAT.name();
    }

    public StatModifier(float firstMin, float firstMax, String stat, ModType type) {
        this.first_min = firstMin;
        this.first_max = firstMax;
        this.stat = stat;
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, float secondMin, float secondMax, Stat stat, ModType type) {

        if (!stat.UsesSecondValue()) {
            try {
                throw new Exception(stat.GUID() + " doesn't need 2nd value for modifiers!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.first_min = firstMin;
        this.first_max = firstMax;
        this.second_min = secondMin;
        this.second_max = secondMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, float secondMin, float secondMax, String stat, ModType type) {

        this.first_min = firstMin;
        this.first_max = firstMax;
        this.second_min = secondMin;
        this.second_max = secondMax;
        this.stat = stat;
        this.type = type.name();
    }

    public Stat GetStat() {
        return SlashRegistry.Stats()
            .get(stat);
    }

    public boolean usesNumberRanges() {
        return getModType()
            .equals(ModType.FLAT) && second_max != 0;
    }

    public MutableText getRangeToShow(int lvl) {

        int fmin = (int) first_min;
        int fmax = (int) first_max;

        if (getModType().isFlat()) {
            fmin = (int) GetStat().scale(first_min, lvl);
            fmax = (int) GetStat().scale(first_max, lvl);
        }
        String text = fmin + "-" + fmax;

        if (GetStat().UsesSecondValue() && getModType().isFlat()) {
            int smin = (int) GetStat().scale(second_min, lvl);
            int smax = (int) GetStat().scale(second_max, lvl);
            text += " / " + smin + "-" + smax;
        } else {
            if (GetStat().IsPercent() || getModType().isLocalIncrease()) {
                text = text + "%";
            } else if (getModType().isGlobalIncrease()) {
                text = text + " global";
            }
        }

        return new LiteralText(text).formatted(Formatting.GREEN);

    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("firstMin", first_min);
        json.addProperty("firstMax", first_max);
        json.addProperty("secondMin", second_min);
        json.addProperty("secondMax", second_max);

        json.addProperty("stat", stat);
        json.addProperty("type", ModType.valueOf(type).id);

        return json;
    }

    @Override
    public StatModifier fromJson(JsonObject json) {

        float firstMin = json.get("firstMin")
            .getAsFloat();
        float firstMax = json.get("firstMax")
            .getAsFloat();

        float secondMin = json.get("secondMin")
            .getAsFloat();
        float secondMax = json.get("secondMax")
            .getAsFloat();

        String stat = json.get("stat")
            .getAsString();

        ModType type = ModType.fromString(json.get("type")
            .getAsString());

        return new StatModifier(firstMin, firstMax, secondMin, secondMax, stat, type);

    }

    public List<Text> getEstimationTooltip(int lvl) {

        List<Text> list = new ArrayList<>();

        Text txt = getRangeToShow(lvl).append(" ")
            .append(GetStat().locName());

        list.add(txt);

        return list;
    }

    public ExactStatData ToExactStat(int percent, int lvl) {
        return ExactStatData.fromStatModifier(this, percent, lvl);
    }

}
