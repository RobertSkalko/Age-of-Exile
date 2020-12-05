package com.robertx22.age_of_exile.database.data.level_ranges;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import net.minecraft.util.math.MathHelper;

public class LevelRange implements ISerializable<LevelRange> {
    public static LevelRange SERIALIZER = new LevelRange(0, 0);

    private float start;
    private float end;
    public transient String id_suffix;

    public LevelRange(String id_suffix, float start, float end) {
        this.start = start;
        this.end = end;
        this.id_suffix = id_suffix;
    }

    private LevelRange(float start, float end) {
        this.start = start;
        this.end = end;
    }

    public int getMinLevel() {
        return MathHelper.clamp((int) (start * GameBalanceConfig.get().MAX_LEVEL), 1, Integer.MAX_VALUE);
    }

    public int getMaxLevel() {
        return (int) (end * GameBalanceConfig.get().MAX_LEVEL);
    }

    public float getEndPercent() {
        return end;
    }

    public float getStartPercent() {
        return start;
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
