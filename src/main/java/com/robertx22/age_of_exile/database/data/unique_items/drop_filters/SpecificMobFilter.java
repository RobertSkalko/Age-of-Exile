package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.robertx22.age_of_exile.loot.LootInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

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

    public List<Text> getTooltip(DropFilterData data) {

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("This mobs can drop:"));
        list.add(Registry.ENTITY_TYPE.get(new Identifier(data.id))
            .getName());
        return list;

    }
}
