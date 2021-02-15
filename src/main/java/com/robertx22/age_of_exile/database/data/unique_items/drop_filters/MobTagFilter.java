package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Identifier;

public class MobTagFilter extends DropFilter {

    public static String ID = "mob_tag";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && EntityTypeTags.getTagGroup()
            .getTagOrEmpty(new Identifier(data.id))
            .contains(info.mobKilled.getType());
    }

    @Override
    public String GUID() {
        return ID;
    }
}
