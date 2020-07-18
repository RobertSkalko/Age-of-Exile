package com.robertx22.mine_and_slash.database.data;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityTypeUtils;

import java.util.Locale;

public class EntityConfig implements ISerializedRegistryEntry<EntityConfig>, ISerializable<EntityConfig> {

    public EntityConfig() {

    }

    public EntityConfig(String id, float loot) {
        this.identifier = id;
        this.loot_multi = loot;
    }

    public static EntityConfig EMPTY = new EntityConfig();

    public String identifier = "";

    public double loot_multi = 1F;
    public double exp_multi = 1F;
    public int min_rarity = 0;
    public int max_rarity = 4;
    public int min_lvl = 1;
    public int max_lvl = 10000;
    public double dmg_multi = 1;
    public double hp_multi = 1;
    public double stat_multi = 1;

    @Override
    public String datapackFolder() {
        try {
            if (EntityTypeUtils.EntityType.valueOf(identifier.toUpperCase(Locale.ROOT)) != null) {
                return "mob_types/";
            }
        } catch (IllegalArgumentException e) {
        }

        if (identifier.contains(":")) {
            return "specific_mobs/";
        } else {
            return "all_mobs_in_mod/";
        }
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("identifier", identifier);
        json.addProperty("min_rarity", min_rarity);
        json.addProperty("max_rarity", max_rarity);
        json.addProperty("min_lvl", min_lvl);
        json.addProperty("max_lvl", max_lvl);
        json.addProperty("dmg_multi", dmg_multi);
        json.addProperty("hp_multi", hp_multi);
        json.addProperty("stat_multi", stat_multi);
        json.addProperty("loot_multi", loot_multi);
        json.addProperty("exp_multi", exp_multi);

        return json;

    }

    @Override
    public EntityConfig fromJson(JsonObject json) {

        EntityConfig c = new EntityConfig();

        c.identifier = json.get("identifier")
            .getAsString();
        c.min_rarity = json.get("min_rarity")
            .getAsInt();
        c.max_rarity = json.get("max_rarity")
            .getAsInt();
        c.min_lvl = json.get("min_lvl")
            .getAsInt();
        c.max_lvl = json.get("max_lvl")
            .getAsInt();

        c.dmg_multi = json.get("dmg_multi")
            .getAsFloat();
        c.hp_multi = json.get("hp_multi")
            .getAsFloat();
        c.stat_multi = json.get("stat_multi")
            .getAsFloat();
        c.loot_multi = json.get("loot_multi")
            .getAsFloat();
        c.exp_multi = json.get("exp_multi")
            .getAsFloat();

        return c;

    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.ENTITY_CONFIGS;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Mobs.get(getRarityRank());
    }

}