package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {

        GearBlueprint gearBlueprint = (GearBlueprint) blueprint;

        FilterListWrap<UniqueGear> gen = Database.UniqueGears()
            .getWrapped()
            .of(x -> !x.filters.cantDrop(blueprint.info))
            .of(x -> x.getPossibleGearTypes()
                .contains(gearBlueprint.gearItemSlot.get()));

        return gen.random();

    }

}
