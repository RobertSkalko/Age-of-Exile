package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;

public class UniqueGearPart extends BlueprintPart<UniqueGear> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {

        GearBlueprint gearBlueprint = (GearBlueprint) blueprint;

        FilterListWrap<UniqueGear> gen = SlashRegistry.UniqueGears()
            .getWrapped()
            .ofSpecificGearType(gearBlueprint.gearItemSlot.get()
                .GUID());

        return gen.random();

    }

}
