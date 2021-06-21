package com.robertx22.age_of_exile.aoe_data.database.groups;

import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GearRarityGroupAdder implements ExileRegistryInit {

    static DataGenKey<GearRarityGroup> NON_UNIQUE_KEY = new DataGenKey<>(GearRarityGroups.NON_UNIQUE_ID);
    static DataGenKey<GearRarityGroup> UP_TO_MAGICAL = new DataGenKey<>(GearRarityGroups.UP_TO_MAGICAL);
    static DataGenKey<GearRarityGroup> UP_TO_RARE = new DataGenKey<>(GearRarityGroups.UP_TO_RARE);
    static DataGenKey<GearRarityGroup> UP_TO_EPIC = new DataGenKey<>(GearRarityGroups.UP_TO_EPIC);

    @Override
    public void registerAll() {

        GearRarityGroup normal = new GearRarityGroup(ExileDB.GearRarities()
            .getSerializable()
            .stream()
            .filter(x -> !x.is_unique_item)
            .map(x -> x.GUID())
            .collect(Collectors.toList()), NON_UNIQUE_KEY.GUID());
        normal.addToSerializables();

        GearRarityGroup low = new GearRarityGroup(Arrays.asList(IRarity.COMMON_ID, IRarity.MAGICAL_ID), UP_TO_MAGICAL.GUID());
        low.addToSerializables();

        GearRarityGroup rare = new GearRarityGroup(Arrays.asList(IRarity.COMMON_ID, IRarity.MAGICAL_ID, IRarity.RARE_ID), UP_TO_RARE.GUID());
        rare.addToSerializables();

        GearRarityGroup epic = new GearRarityGroup(Arrays.asList(IRarity.COMMON_ID, IRarity.MAGICAL_ID, IRarity.RARE_ID, IRarity.EPIC_ID), UP_TO_EPIC.GUID());
        epic.addToSerializables();

    }
}
