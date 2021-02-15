package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.loot.LootInfo;

public abstract class DropFilter implements IGUID {

    public abstract boolean canDrop(DropFilterData data, LootInfo info);

}
