package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class MobTagFilter extends DropFilter {

    public static String ID = "mob_tag";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && EntityTypeTags.getAllTags()
            .getTagOrEmpty(new ResourceLocation(data.id))
            .contains(info.mobKilled.getType());
    }

    @Override
    public String GUID() {
        return ID;
    }

    public List<ITextComponent> getTooltip(DropFilterData data) {

        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent("Mobs with this tag can drop:"));
        list.add(new StringTextComponent(data.id));
        return list;

    }
}
