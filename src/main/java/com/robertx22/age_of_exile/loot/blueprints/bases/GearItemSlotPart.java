package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;

public class GearItemSlotPart extends BlueprintPart<BaseGearType, GearBlueprint> {

    public GearItemSlotPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected BaseGearType generateIfNull() {

        GearSlot slot = Database.GearSlots()
            .random();

        return Database.GearTypes()
            .getFilterWrapped(x -> x.getGearSlot()
                .GUID()
                .equals(slot.GUID()) && x.getLevelRange()
                .isLevelInRange(blueprint.level.number))
            .random();
    }

    public void set(String id) {
        super.set(Database.GearTypes()
            .get(id));
    }

}


