package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.exiled_lib.registry.FilterListWrap;
import com.robertx22.exiled_lib.registry.SlashRegistry;

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
