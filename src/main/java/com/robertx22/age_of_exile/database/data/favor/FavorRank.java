package com.robertx22.age_of_exile.database.data.favor;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;

import java.util.ArrayList;
import java.util.List;

public class FavorRank implements ISerializedRegistryEntry<FavorRank>, IAutoGson<FavorRank> {

    public static FavorRank SERIALIZER = new FavorRank();

    String id = "";

    // excludes min and max
    public int min = 0;

    public int rank = 0;

    public boolean drop_unique_gears = true;
    public boolean drop_runes = true;
    public boolean drop_gems = true;
    public boolean drop_currency = true;
    public boolean drop_lvl_rewards = true;

    public boolean can_salvage_loot = true;

    public int extra_favor_drain = 0;

    public float loot_multi = 1;
    public float exp_multi = 1;

    public List<String> excludedRarities = new ArrayList<>();

    @Override
    public Class<FavorRank> getClassForSerialization() {
        return FavorRank.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.FAVOR_RANK;
    }

    @Override
    public String GUID() {
        return id;
    }
}
