package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class UniqueGearPart extends BlueprintPart<IUnique> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected IUnique generateIfNull() {

        GearBlueprint gearBlueprint = (GearBlueprint) blueprint;

        FilterListWrap<IUnique> gen = SlashRegistry.UniqueGears()
            .getWrapped()
            .ofSpecificGearType(gearBlueprint.gearItemSlot.get()
                .GUID());

        return gen.random();

    }

}
