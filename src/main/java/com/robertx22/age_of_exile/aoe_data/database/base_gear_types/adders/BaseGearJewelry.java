package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseGearJewelry implements ExileRegistryInit, GearDataHelper {

    public static DataGenKey<BaseGearType> RING;
    public static DataGenKey<BaseGearType> NECKLACE;

    @Override
    public void registerAll() {

        RING = BaseGearBuilder.of(GearSlots.RING, "ring", "Ring")
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .build();

        NECKLACE = BaseGearBuilder.of(GearSlots.NECKLACE, "necklace", "Necklace")
            .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
            .build();

    }
}

