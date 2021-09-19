package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mixins.AccessorItemTags;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

public class ModItemTags {

    public static final Tag.Named<Item> CHIPPED_GEMS = get("chipped_gems");
    public static final Tag.Named<Item> FLAWED_GEMS = get("flawed_gems");
    public static final Tag.Named<Item> REGULAR_GEMS = get("regular_gems");

    public static void init() {

    }

    private static Tag.Named<Item> get(String id) {
        return AccessorItemTags.callRegister(Ref.MODID + ":" + id);
    }
}
