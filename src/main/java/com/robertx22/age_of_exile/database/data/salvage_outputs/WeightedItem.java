package com.robertx22.age_of_exile.database.data.salvage_outputs;

import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class WeightedItem implements IWeighted {

    String item;
    int weight;

    public WeightedItem(Item item, int weight) {
        this.item = Registry.ITEM.getKey(item)
            .toString();
        this.weight = weight;
    }

    public Item getItem() {
        return Registry.ITEM.get(new ResourceLocation(item));
    }

    @Override
    public int Weight() {
        return weight;
    }
}
