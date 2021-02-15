package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.util.registry.Registry;

public class SpecificMobFilter extends DropFilter {

    public static String ID = "specific_mob";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && Registry.ENTITY_TYPE.getId(info.mobKilled.getType())
            .toString()
            .equals(data.id);
    }

    @Override
    public String GUID() {
        return ID;
    }
}
