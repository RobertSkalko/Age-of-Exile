package com.robertx22.age_of_exile.database.data.level_ranges;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;

public class LevelRange implements ISerializable<LevelRange> {
    public static LevelRange SERIALIZER = new LevelRange(0, 0);

    private float start;
    private float end;

    public LevelRange(float start, float end) {
        this.start = start;
        this.end = end;

    }

    public int getMinLevel() {
        return (int) (start * ModConfig.get().Server.MAX_LEVEL);
    }

    public int getMaxLevel() {
        return (int) (end * ModConfig.get().Server.MAX_LEVEL);
    }

    public float getEndPercent() {
        return end;
    }

    public boolean isLevelInRange(int lvl) {
        return lvl >= getMinLevel() && lvl <= getMaxLevel();
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("start", start);
        json.addProperty("end", end);
        return json;
    }

    @Override
    public LevelRange fromJson(JsonObject json) {
        return new LevelRange(json.get("start")
            .getAsFloat(), json.get("end")
            .getAsFloat());
    }
}
