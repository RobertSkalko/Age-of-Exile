package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;

public class GearItemSlotPart extends BlueprintPart<BaseGearType> {

    public GearItemSlotPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected BaseGearType generateIfNull() {
        return SlashRegistry.GearTypes().random();
    }

    public void set(String id) {
        super.set(SlashRegistry.GearTypes().get(id));
    }

}


