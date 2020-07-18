package com.robertx22.mine_and_slash.database.data;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class MinMax implements ISerializable<MinMax> {

    public static ISerializable<MinMax> getSerializer() {
        return new MinMax(0, 0);
    }

    public final int min;
    public final int max;

    public MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(int num) {

        if (num >= min) {
            if (num <= max) {
                return true;
            }
        }
        return false;

    }

    public int random() {
        return RandomUtils.RandomRange(min, max);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("min", min);
        json.addProperty("max", max);

        return json;
    }

    public boolean isEmpty() {
        return min == 0 && max == 0;
    }

    @Override
    public MinMax fromJson(JsonObject json) {
        int mi = json.get("min")
            .getAsInt();
        int ma = json.get("max")
            .getAsInt();

        return new MinMax(mi, ma);
    }
}
