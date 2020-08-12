package com.robertx22.age_of_exile.database.data.affixes;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.datapacks.JsonUtils;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;

import java.util.List;

public class AffixTier implements ISerializable<AffixTier>, IWeighted {

    List<StatModifier> mods;
    int weight = 1000;
    public int tier;

    public static AffixTier EMPTY = new AffixTier(null, 0, 0);

    public AffixTier(List<StatModifier> mods, int weight, int tier) {
        this.mods = mods;
        this.weight = weight;
        this.tier = tier;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        JsonUtils.addStats(mods, json, "mods");
        json.addProperty("weight", weight);
        json.addProperty("tier", tier);
        return json;
    }

    @Override
    public AffixTier fromJson(JsonObject json) {
        return new AffixTier(JsonUtils.getStats(json, "mods"), json.get("weight")
            .getAsInt(), json.get("tier")
            .getAsInt());

    }

    @Override
    public int Weight() {
        return weight;
    }
}
