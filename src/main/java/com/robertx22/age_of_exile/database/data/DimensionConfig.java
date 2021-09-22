package com.robertx22.age_of_exile.database.data;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public class DimensionConfig implements JsonExileRegistry<DimensionConfig>, ISerializable<DimensionConfig> {

    public DimensionConfig() {
    }

    public static DimensionConfig EMPTY = new DimensionConfig();

    public DimensionConfig(int startlvl, String dimension_id) {
        this.min_lvl = startlvl;
        this.dimension_id = dimension_id;
    }

    public DimensionConfig(String dimension_id, int min_lvl, int max_lvl) {
        this.dimension_id = dimension_id;
        this.min_lvl = min_lvl;
        this.max_lvl = max_lvl;
    }

    public static DimensionConfig Overworld() {
        DimensionConfig c = new DimensionConfig(1, "minecraft:overworld");
        c.min_lvl = 1;
        c.max_lvl = 50;
        return c;
    }

    public static DimensionConfig Nether() {
        DimensionConfig d = new DimensionConfig(10, "minecraft:the_nether").setMobTier(2);
        d.min_lvl = 10;
        d.max_lvl = 50;
        return d;
    }

    public static DimensionConfig End() {
        DimensionConfig d = new DimensionConfig(10, "minecraft:the_end").setMobTier(3);
        d.min_lvl = 20;
        d.max_lvl = 50;
        return d;
    }

    public DimensionConfig setDistPerLevel(int dist) {
        this.mob_lvl_per_distance = dist;
        return this;
    }

    public static DimensionConfig DefaultExtra() {
        DimensionConfig config = new DimensionConfig();
        config.min_lvl = 1;
        config.max_lvl = 25;
        return config;
    }

    public DimensionConfig setMobTier(int t) {
        this.mob_tier = t;
        return this;
    }

    public String dimension_id = "default";

    public int mob_tier = 0;

    public float all_drop_multi = 1F;
    public float exp_multi = 1F;

    public float unique_gear_drop_multi = 1F;

    public float mob_strength_multi = 1F;

    public int min_lvl = 1;
    public int max_lvl = Integer.MAX_VALUE;
    public int mob_lvl_per_distance = 100;
    public int min_lvl_area = 100;
    public boolean scale_to_nearest_player = false;

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.DIMENSION_CONFIGS;
    }

    @Override
    public String GUID() {
        return dimension_id;
    }

    @Override
    public int Weight() {
        return 1;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("dimension_id", dimension_id);
        json.addProperty("mob_tier", mob_tier);
        json.addProperty("exp_multi", exp_multi);
        json.addProperty("all_drop_multi", all_drop_multi);
        json.addProperty("mob_lvl_per_distance", mob_lvl_per_distance);
        json.addProperty("unique_gear_drop_multi", unique_gear_drop_multi);
        json.addProperty("mob_strength_multi", mob_strength_multi);
        json.addProperty("min_lvl", min_lvl);
        json.addProperty("max_lvl", max_lvl);
        json.addProperty("scale_to_nearest_player", scale_to_nearest_player);

        return json;
    }

    @Override
    public DimensionConfig fromJson(JsonObject json) {

        try {

            DimensionConfig config = new DimensionConfig();

            config.dimension_id = json.get("dimension_id")
                .getAsString();
            config.mob_tier = json.get("mob_tier")
                .getAsInt();
            config.all_drop_multi = json.get("all_drop_multi")
                .getAsFloat();
            if (json.has("exp_multi")) {
                config.exp_multi = json.get("exp_multi")
                    .getAsFloat();
            }
            config.unique_gear_drop_multi = json.get("unique_gear_drop_multi")
                .getAsFloat();
            config.mob_lvl_per_distance = json.get("mob_lvl_per_distance")
                .getAsInt();
            config.mob_strength_multi = json.get("mob_strength_multi")
                .getAsFloat();
            config.min_lvl = json.get("min_lvl")
                .getAsInt();
            config.max_lvl = json.get("max_lvl")
                .getAsInt();
            config.scale_to_nearest_player = json.get("scale_to_nearest_player")
                .getAsBoolean();

            return config;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
