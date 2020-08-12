package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class GearSlotDatapackLoader extends BaseDataPackLoader<GearSlot> {
    static String ID = "gear_slot";

    public GearSlotDatapackLoader() {
        super(SlashRegistryType.GEAR_SLOT, ID, x -> GearSlot.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<GearSlot>(SlashRegistry.GearSlots()
            .getSerializable(), ID);
    }
}
