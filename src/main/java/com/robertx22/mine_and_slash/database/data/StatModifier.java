package com.robertx22.mine_and_slash.database.data;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class StatModifier implements ISerializable<StatModifier> {

    public float firstMin = 0;
    public float firstMax = 0;

    public float secondMin = 0;
    public float secondMax = 0;

    public String stat;
    public String type;

    public static StatModifier EMPTY = new StatModifier();

    private StatModifier() {

    }

    public StatModifier(float firstMin, float firstMax, Stat stat, ModType type) {
        this.firstMin = firstMin;
        this.firstMax = firstMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, String stat, ModType type) {
        this.firstMin = firstMin;
        this.firstMax = firstMax;
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

        this.firstMin = firstMin;
        this.firstMax = firstMax;
        this.secondMin = secondMin;
        this.secondMax = secondMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatModifier(float firstMin, float firstMax, float secondMin, float secondMax, String stat, ModType type) {

        this.firstMin = firstMin;
        this.firstMax = firstMax;
        this.secondMin = secondMin;
        this.secondMax = secondMax;
        this.stat = stat;
        this.type = type.name();
    }

    public Stat GetStat() {
        return SlashRegistry.Stats()
            .get(stat);
    }

    public boolean usesNumberRanges() {
        return getModType()
            .equals(ModType.FLAT) && secondMax != 0;
    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("firstMin", firstMin);
        json.addProperty("firstMax", firstMax);
        json.addProperty("secondMin", secondMin);
        json.addProperty("secondMax", secondMax);

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

    public ExactStatData ToExactStat(int percent, int lvl) {
        return ExactStatData.fromStatModifier(this, percent, lvl);
    }

}
