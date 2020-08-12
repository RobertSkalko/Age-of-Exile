package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import net.minecraft.item.Item;

import java.util.HashMap;

public class Cached {

    public static Integer maxTier = null;

    public static HashMap<Item, Boolean> IS_COMP_ITEM_MAP = new HashMap<>();

    public static void reset() {
        maxTier = null;
        IS_COMP_ITEM_MAP.clear();
    }

}
