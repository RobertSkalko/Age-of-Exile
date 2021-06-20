package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.text.Text;

import java.util.List;

public abstract class DropFilter implements IGUID {

    public abstract boolean canDrop(DropFilterData data, LootInfo info);

    public abstract List<Text> getTooltip(DropFilterData data);
}
