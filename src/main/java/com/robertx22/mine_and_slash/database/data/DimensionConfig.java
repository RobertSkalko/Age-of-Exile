package com.robertx22.mine_and_slash.database.data;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;

public class DimensionConfig implements ISerializedRegistryEntry<DimensionConfig>, ISerializable<DimensionConfig> {

    public DimensionConfig() {

    }

    public static DimensionConfig EMPTY = new DimensionConfig();

    public DimensionConfig(int startlvl, String dimension_id) {
        this.min_lvl = startlvl;
        this.dimension_id = dimension_id;
    }

    public static DimensionConfig Overworld() {
        return new DimensionConfig(1, "minecraft:overworld");
    }

    public static DimensionConfig Nether() {
        return new DimensionConfig(15, "minecraft:the_nether").setMobTier(2);
    }

    public static DimensionConfig End() {
        return new DimensionConfig(20, "minecraft:the_end").setMobTier(2);
    }

    public static DimensionConfig DefaultExtra() {
        DimensionConfig config = new DimensionConfig();
        return config;
    }

    public DimensionConfig setUniqueMulti(float m) {
        this.unique_gear_drop_multi = m;
        return this;
    }

    public DimensionConfig setMobTier(int t) {
        this.mob_tier = t;
        return this;
    }

    public String dimension_id = "";

    public boolean drops_unique_gear = true;

    public int mob_tier = 0;

    public float all_drop_multi = 1F;

    public float unique_gear_drop_multi = 1F;

    public float mob_strength_multi = 1F;

    public int min_lvl = 1;
    public int max_lvl = Integer.MAX_VALUE;
    public int mob_lvl_per_distance = 100;
    public boolean scale_to_nearest_player = false;

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.DIMENSION_CONFIGS;
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
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    @Override
    public int getTier() {
        return this.mob_tier;
    }

    public boolean isMapWorld() {
        return mob_tier > 0;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("dimension_id", dimension_id);
        json.addProperty("drops_unique_gear", drops_unique_gear);
        json.addProperty("mob_tier", mob_tier);
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
            config.drops_unique_gear = json.get("drops_unique_gear")
                .getAsBoolean();
            config.mob_tier = json.get("mob_tier")
                .getAsInt();
            config.all_drop_multi = json.get("all_drop_multi")
                .getAsFloat();
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
