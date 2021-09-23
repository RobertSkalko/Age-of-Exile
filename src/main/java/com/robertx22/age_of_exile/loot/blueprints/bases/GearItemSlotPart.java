package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;

import java.util.List;
import java.util.stream.Collectors;

public class GearItemSlotPart extends BlueprintPart<BaseGearType, GearBlueprint> {

    public GearItemSlotPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected BaseGearType generateIfNull() {

        GearSlot slot = ExileDB.GearSlots()
            .random();

        List<BaseGearType> all = ExileDB.GearTypes()
            .getList();

        List<BaseGearType> filt = all.stream()
            .filter(x -> x.getGearSlot()
                .GUID()
                .equals(slot.id))
            .collect(Collectors.toList());

        return ExileDB.GearTypes()
            .getFilterWrapped(x -> x.getGearSlot()
                .GUID()
                .equals(slot.GUID()) && x.getLevelRange()
                .isLevelInRange(blueprint.level.number))
            .random();
    }

    public void set(String id) {
        super.set(ExileDB.GearTypes()
            .get(id));
    }

}


