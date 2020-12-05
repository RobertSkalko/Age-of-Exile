package com.robertx22.age_of_exile.database.data;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
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
    public float min1 = 0;
    @Store
    public float max1 = 0;

    @Store
    public float min2 = 0;
    @Store
    public float max2 = 0;

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
        mod.min1 = buf.readFloat();
        mod.max1 = buf.readFloat();
        mod.min2 = buf.readFloat();
        mod.max2 = buf.readFloat();

        mod.stat = buf.readString(100);
        mod.type = buf.readString(100);
        return mod;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeFloat(min1);
        buf.writeFloat(max1);
        buf.writeFloat(min2);
        buf.writeFloat(max2);

        buf.writeString(stat, 100);
        buf.writeString(type, 100);

    }

    public StatModifier(float firstMin, float firstMax, Stat stat, ModType type) {
        this.min1 = firstMin;
        this.max1 = firstMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, Stat stat) {
        this.min1 = firstMin;
        this.max1 = firstMax;
        this.stat = stat.GUID();
        this.type = ModType.FLAT.name();
    }

    public StatModifier(float firstMin, float firstMax, String stat, ModType type) {
        this.min1 = firstMin;
        this.max1 = firstMax;
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

        this.min1 = firstMin;
        this.max1 = firstMax;
        this.min2 = secondMin;
        this.max2 = secondMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, float secondMin, float secondMax, String stat, ModType type) {

        this.min1 = firstMin;
        this.max1 = firstMax;
        this.min2 = secondMin;
        this.max2 = secondMax;
        this.stat = stat;
        this.type = type.name();
    }

    public Stat GetStat() {
        return Database.Stats()
            .get(stat);
    }

    public boolean usesNumberRanges() {
        return getModType()
            .equals(ModType.FLAT) && max2 != 0;
    }

    public MutableText getRangeToShow(int lvl) {

        int fmin = (int) min1;
        int fmax = (int) max1;

        if (getModType().isFlat()) {
            fmin = (int) GetStat().scale(min1, lvl);
            fmax = (int) GetStat().scale(max1, lvl);
        }
        String text = fmin + "/" + fmax;

        if (GetStat().UsesSecondValue() && getModType().isFlat()) {
            int smin = (int) GetStat().scale(min2, lvl);
            int smax = (int) GetStat().scale(max2, lvl);
            text += " / " + smin + "-" + smax;
        } else {
            if (GetStat().IsPercent() || getModType().isLocalIncrease()) {
                text = text + "%";
            } else if (getModType().isGlobalIncrease()) {
                text = text + " Global";
            }
        }

        return new LiteralText("(").formatted(Formatting.GREEN)
            .append(text)
            .append(")");

    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("min1", min1);
        json.addProperty("max1", max1);

        if (Database.Stats()
            .isRegistered(stat) && GetStat().UsesSecondValue() && getModType().isFlat()) {
            json.addProperty("min2", min2);
            json.addProperty("max2", max2);
        }

        json.addProperty("stat", stat);
        json.addProperty("type", ModType.valueOf(type).id);

        return json;
    }

    @Override
    public StatModifier fromJson(JsonObject json) {

        float firstMin = json.get("min1")
            .getAsFloat();
        float firstMax = json.get("max1")
            .getAsFloat();

        float secondMin = json.has("min2") ? json.get("min2")
            .getAsFloat() : 0;
        float secondMax = json.has("min2") ? json.get("max2")
            .getAsFloat() : 0;

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
