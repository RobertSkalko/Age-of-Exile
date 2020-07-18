package com.robertx22.mine_and_slash.database.data.tiers.base;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;

public class Tier implements ISerializedRegistryEntry<Tier>, ISerializable<Tier> {

    public static ISerializable<Tier> SERIALIZER = new Tier(1);

    public float mob_health_multi = 1;
    public float mob_damage_multi = 1;
    public float mob_stat_multi = 1;

    public float loot_multi = 1;
    public float chance_for_higher_drop_rarity = 0;

    public int id_rank;

    @Override
    public boolean isFromDatapack() {
        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("id_rank", id_rank);

        json.addProperty("mob_health_multi", mob_health_multi);
        json.addProperty("mob_damage_multi", mob_damage_multi);
        json.addProperty("mob_stat_multi", mob_stat_multi);

        json.addProperty("loot_multi", loot_multi);
        json.addProperty("chance_for_higher_drop_rarity", chance_for_higher_drop_rarity);

        return json;
    }

    @Override
    public Tier fromJson(JsonObject json) {
        Tier tier = new Tier();

        tier.id_rank = json.get("id_rank")
            .getAsInt();

        tier.mob_damage_multi = json.get("mob_damage_multi")
            .getAsFloat();
        tier.mob_health_multi = json.get("mob_health_multi")
            .getAsFloat();
        tier.mob_stat_multi = json.get("mob_stat_multi")
            .getAsFloat();

        tier.loot_multi = json.get("loot_multi")
            .getAsFloat();
        tier.chance_for_higher_drop_rarity = json.get("chance_for_higher_drop_rarity")
            .getAsFloat();

        return tier;
    }

    public Tier(int id_rank) {
        this.id_rank = id_rank;

        this.setLootPerRank();
        this.setMobStrengthPerRank();
    }

    private Tier() {

    }

    protected void setMobStrengthPerRank() {
        this.mob_damage_multi = 1F + 0.4F * id_rank;
        this.mob_health_multi = 1F + 0.5F * id_rank;
        this.mob_stat_multi = 1F + 0.3F * id_rank;
    }

    protected void setLootPerRank() {
        this.loot_multi = 1F + 0.05F * id_rank;
        this.chance_for_higher_drop_rarity = id_rank * 5;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.TIER;
    }

    @Override
    public String GUID() {
        return id_rank + "";
    }

}
