package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class GearSlotKey extends DataKey<GearSlot> {
    public GearSlotKey(String id) {
        super(id);
        AllDataKeys.GEAR_SLOTS.add(this);
    }

    @Override
    public GearSlot getFromRegistry() {
        return ExileDB.GearSlots()
            .get(id);
    }

    @Override
    public GearSlot getFromDataGen() {
        return ExileDB.GearSlots()
            .getFromSerializables(id);
    }

    @Override
    public Class getDataClass() {
        return GearSlot.class;
    }
}
