package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GearSalvageConfig {

    public int BASE_ITEM_VALUE = 500;

    public int VALUE_ADDED_PER_AFFIX = 100;
    public int VALUE_ADDED_PER_SOCKET = 50;
    public int VALUE_ADDED_PER_FULL_SOCKET = 150;

    public LinearScalingConfig VALUE_PER_LEVEL_SCALING = new LinearScalingConfig(0.02D);

    List<ItemAndPrice> SALVAGE_RESULTS = new ArrayList<ItemAndPrice>() {
        {
            add(new ItemAndPrice(500, ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, 1000));
            add(new ItemAndPrice(1000, ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, 200));
            add(new ItemAndPrice(500, Items.IRON_INGOT, 50));
        }
    };

    public GearSalvageConfig() {
    }

    public List<ItemStack> getResult(GearItemData gear) {
        return getResultForValue(getValue(gear));
    }

    private int getValue(GearItemData gear) {

        int value = BASE_ITEM_VALUE;

        value += gear.sockets.sockets.size() * VALUE_ADDED_PER_FULL_SOCKET;
        value += gear.sockets.getEmptySockets() * VALUE_ADDED_PER_SOCKET;
        value += gear.affixes.getNumberOfAffixes() * VALUE_ADDED_PER_AFFIX;

        value *= gear.getRarity()
            .valueMulti();

        return value;
    }

    private List<ItemStack> getResultForValue(int value) {
        List<ItemStack> stacks = new ArrayList<>();

        int smallest = SALVAGE_RESULTS.stream()
            .min(Comparator.comparingInt(x -> x.price))
            .get().price;

        while (value >= smallest) {

            List<ItemAndPrice> list = new ArrayList<>();
            for (ItemAndPrice x : SALVAGE_RESULTS) {
                if (x.price <= value) {
                    list.add(x);
                }
            }
            ItemAndPrice random = RandomUtils.weightedRandom(list);
            stacks.add(new ItemStack(random.getItem()));
            value -= random.price;
        }

        return stacks;

    }

}
