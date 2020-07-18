package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static Item.Settings getDefaultGearProperties() {

        Item.Settings prop = new Item.Settings();

        return prop;
    }

    public static Item randomMagicEssence() {
        List<IWeighted> list = Arrays.asList((IWeighted) ModRegistry.MISC_ITEMS.MAGIC_ESSENCE.get(), (IWeighted) ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE.get());
        return (Item) RandomUtils.weightedRandom(list);

    }
}
