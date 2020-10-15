package com.robertx22.age_of_exile.aoe_data.database.groups;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GearRarityGroupAdder implements ISlashRegistryInit {

    public static DataGenKey<GearRarityGroup> NON_UNIQUE_KEY = new DataGenKey<GearRarityGroup>("non_unique");
    public static DataGenKey<GearRarityGroup> LOW_KEY = new DataGenKey<GearRarityGroup>("low");

    @Override
    public void registerAll() {

        GearRarityGroup normal = new GearRarityGroup(SlashRegistry.GearRarities()
            .getSerializable()
            .stream()
            .filter(x -> !x.isUnique())
            .map(x -> x.GUID())
            .collect(Collectors.toList()), NON_UNIQUE_KEY.GUID());
        normal.addToSerializables();

        GearRarityGroup low = new GearRarityGroup(Arrays.asList(IRarity.COMMON_ID, IRarity.MAGICAL_ID), LOW_KEY.GUID());

    }
}
