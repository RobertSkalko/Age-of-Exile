package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Cached {

    public static List<ImmutablePair<EntityAttribute, UUID>> VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();
    public static Integer maxTier = null;

    public static HashMap<Item, CompatibleItemUtils.Data> COMPATIBLE_ITEMS = new HashMap<>();

    public static void reset() {
        maxTier = null;

        COMPATIBLE_ITEMS.clear();
    }

}
