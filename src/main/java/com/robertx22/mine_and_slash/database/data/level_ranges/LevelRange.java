package com.robertx22.mine_and_slash.database.data.level_ranges;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;

public class LevelRange implements ISerializedRegistryEntry<LevelRange>, ISerializable<LevelRange> {
    public static LevelRange SERIALIZER = new LevelRange(0, 0, "");

    private float start;
    private float end;
    private String id;

    // todo default ingot item needed to craft stuff for these,
    //THIS WOULDNT BE SERIALIZED CUS RECIPES ARE DATPACKS TOO

    public LevelRange(float start, float end, String id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public int getMinLevel() {
        return (int) (start * ModConfig.get().Server.MAX_LEVEL);
    }

    public int getMaxLevel() {
        return (int) (end * ModConfig.get().Server.MAX_LEVEL);
    }

    public boolean isLevelInRange(int lvl) {
        return lvl >= getMinLevel() && lvl <= getMaxLevel();
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.LEVEl_RANGE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("start", start);
        json.addProperty("end", end);
        return json;
    }

    @Override
    public LevelRange fromJson(JsonObject json) {
        return new LevelRange(json.get("start")
            .getAsFloat(), json.get("end")
            .getAsFloat(), json.get("id")
            .getAsString());
    }
}
