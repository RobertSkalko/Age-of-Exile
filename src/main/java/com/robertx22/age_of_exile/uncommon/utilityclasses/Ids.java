package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Ids {
    public static ResourceLocation item(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
