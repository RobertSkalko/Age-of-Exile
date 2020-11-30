package com.robertx22.age_of_exile.database.data.groups;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearRarityGroup implements ISerializedRegistryEntry<GearRarityGroup>, IAutoGson<GearRarityGroup> {
    public static GearRarityGroup SERIALIZER = new GearRarityGroup();

    List<String> rarities = new ArrayList<>();
    String id = "";

    public List<GearRarity> getRarities() {
        return rarities.stream()
            .map(x -> Database.GearRarities()
                .get(x))
            .collect(Collectors.toList());
    }

    public GearRarityGroup(List<String> rarities, String id) {
        this.rarities = rarities;
        this.id = id;
    }

    public GearRarityGroup() {
    }

    @Override
    public Class<GearRarityGroup> getClassForSerialization() {
        return GearRarityGroup.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEAR_RARITY_GROUP;
    }

    @Override
    public String GUID() {
        return id;
    }
}
