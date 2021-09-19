package com.robertx22.age_of_exile.database.data.player_skills;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemCraftExp {

    public int exp = 0;
    public String item_id = "";

    public ItemCraftExp(int exp, Item item) {
        this.exp = exp;
        this.item_id = Registry.ITEM.getKey(item)
            .toString();
    }

    public ItemCraftExp() {
    }

    public Item getItem() {
        return Registry.ITEM.get(new ResourceLocation(item_id));
    }

}
