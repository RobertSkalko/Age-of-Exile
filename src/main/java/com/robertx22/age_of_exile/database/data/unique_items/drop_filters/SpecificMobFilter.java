package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SpecificMobFilter extends DropFilter {

    public static String ID = "specific_mob";

    @Override
    public boolean canDrop(DropFilterData data, LootInfo info) {
        return info.mobKilled != null && Registry.ENTITY_TYPE.getKey(info.mobKilled.getType())
            .toString()
            .equals(data.id);
    }

    @Override
    public String GUID() {
        return ID;
    }

    public List<ITextComponent> getTooltip(DropFilterData data) {

        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent("This mobs can drop:"));
        list.add(Registry.ENTITY_TYPE.get(new ResourceLocation(data.id))
            .getDescription());
        return list;

    }
}
