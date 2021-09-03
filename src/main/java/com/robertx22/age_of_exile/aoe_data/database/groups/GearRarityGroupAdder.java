package com.robertx22.age_of_exile.aoe_data.database.groups;

import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.stream.Collectors;

public class GearRarityGroupAdder implements ExileRegistryInit {

    static DataGenKey<GearRarityGroup> NON_UNIQUE_KEY = new DataGenKey<>(GearRarityGroups.NON_UNIQUE_ID);
    static DataGenKey<GearRarityGroup> DROPPABLE_RARITIES = new DataGenKey<>(GearRarityGroups.DROPPABLE_RARITIES_ID);

    @Override
    public void registerAll() {

        GearRarityGroup normal = new GearRarityGroup(ExileDB.GearRarities()
            .getSerializable()
            .stream()
            .filter(x -> !x.is_unique_item)
            .map(x -> x.GUID())
            .collect(Collectors.toList()), NON_UNIQUE_KEY.GUID());
        normal.addToSerializables();

        GearRarityGroup all = new GearRarityGroup(ExileDB.GearRarities()
            .getSerializable()
            .stream()
            .map(x -> x.GUID())
            .collect(Collectors.toList()), DROPPABLE_RARITIES.GUID());
        all.addToSerializables();

    }
}
