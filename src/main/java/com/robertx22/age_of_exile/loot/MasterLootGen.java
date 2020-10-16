package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.loot.generators.*;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MasterLootGen {

    public static List<ItemStack> generateLoot(LootInfo info) {
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (info == null) {
            return items;
        }

        items = populateOnce(info);

        int tries = 0;

        while (items.size() < info.minItems) {

            tries++;
            if (tries > 20) {
                System.out.println("Tried to generate loot many times but failed! " + info.toString());
                break;
            }
            List<ItemStack> extra = populateOnce(info);
            if (!extra.isEmpty()) {
                items.add(RandomUtils.randomFromList(extra));
            }
        }

        while (items.size() > info.maxItems) {
            ItemStack randomtoremove = RandomUtils.randomFromList(items);
            items.removeIf(x -> x.equals(randomtoremove));
        }

        return items;
    }

    private static List<ItemStack> populateOnce(LootInfo info) {
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (info == null) {
            return items;
        }

        items.addAll(new CurrencyLootGen(info).tryGenerate());
        items.addAll(new GearLootGen(info).tryGenerate());
        items.addAll(new GemLootGen(info).tryGenerate());
        items.addAll(new RuneLootGen(info).tryGenerate());
        items.addAll(new VanillaRewardsLootGen(info).tryGenerate());

        return items.stream()
            .filter(x -> x != null && !x.isEmpty())
            .collect(Collectors.toList());
    }

    public static List<ItemStack> generateLoot(LivingEntity victim, PlayerEntity killer) {

        LootInfo info = LootInfo.ofMobKilled(killer, victim);
        List<ItemStack> items = generateLoot(info);

        return items;
    }

    public static void genAndDrop(LivingEntity victim, PlayerEntity killer) {
        List<ItemStack> items = generateLoot(victim, killer);
        for (ItemStack stack : items) {
            victim.dropStack(stack, 1F);
        }
    }

}
