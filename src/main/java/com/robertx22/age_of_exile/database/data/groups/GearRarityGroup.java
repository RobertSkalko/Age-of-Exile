package com.robertx22.age_of_exile.database.data.groups;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearRarityGroup implements JsonExileRegistry<GearRarityGroup>, IAutoGson<GearRarityGroup> {
    public static GearRarityGroup SERIALIZER = new GearRarityGroup();

    List<String> rarities = new ArrayList<>();
    String id = "";

    public List<GearRarity> getRarities() {
        return rarities.stream()
            .map(x -> ExileDB.GearRarities()
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
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_RARITY_GROUP;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
