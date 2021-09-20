package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {

    public static final Tag.INamedTag<Item> CHIPPED_GEMS = get("chipped_gems");
    public static final Tag.INamedTag<Item> FLAWED_GEMS = get("flawed_gems");
    public static final Tag.INamedTag<Item> REGULAR_GEMS = get("regular_gems");

    public static void init() {

    }

    private static Tag.INamedTag<Item> get(String id) {
        return ItemTags.createOptional(new ResourceLocation(SlashRef.MODID + ":" + id));
    }
}
