package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;

import java.util.ArrayList;
import java.util.List;

public class DungeonMobList implements ISerializedRegistryEntry<DungeonMobList>, IAutoGson<DungeonMobList> {
    public static DungeonMobList SERIALIZER = new DungeonMobList();

    public String id = "";

    public List<WeightedMobEntry> mobs = new ArrayList<>();
    public List<WeightedMobEntry> bosses = new ArrayList<>();

    @Override
    public Class<DungeonMobList> getClassForSerialization() {
        return DungeonMobList.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.DUNGEON_MOB_LIST;
    }

    @Override
    public String GUID() {
        return id;
    }
}
