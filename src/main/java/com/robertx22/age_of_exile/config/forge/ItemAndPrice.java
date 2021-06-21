package com.robertx22.age_of_exile.config.forge;

import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemAndPrice implements IWeighted {

    public int price;
    public String item_id;
    public int weight;

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    public ItemAndPrice() {
    }

    public ItemAndPrice(int price, Item item, int weight) {
        this.price = price;
        this.item_id = Registry.ITEM.getId(item)
            .toString();
        this.weight = weight;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
