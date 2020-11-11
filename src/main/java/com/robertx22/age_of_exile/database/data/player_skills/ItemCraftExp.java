package com.robertx22.age_of_exile.database.data.player_skills;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemCraftExp {

    public float exp = 0;
    public String item_id = "";

    public ItemCraftExp(float exp, Item item) {
        this.exp = exp;
        this.item_id = Registry.ITEM.getId(item)
            .toString();
    }

    public ItemCraftExp() {
    }

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

}
