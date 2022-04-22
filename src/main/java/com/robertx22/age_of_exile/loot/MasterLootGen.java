package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.loot.generators.CurrencyLootGen;
import com.robertx22.age_of_exile.loot.generators.GearSoulLootGen;
import com.robertx22.age_of_exile.loot.generators.GemLootGen;
import com.robertx22.age_of_exile.loot.generators.RuneLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MasterLootGen {

    public static List<ItemStack> generateLoot(LootInfo info) {
        List<ItemStack> items = new ArrayList<>();
        try {

            if (info == null) {
                return items;
            }

            items = populateOnce(info);

            int tries = 0;

            while (items.size() < info.getMinItems()) {

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

            tries = 0;

            while (items.size() > info.getMaxItems()) {
                tries++;
                if (tries > 50) {
                    System.out.println("Took too many tries to remove items from masterlootgen");
                    break;
                }
                items.remove(RandomUtils.RandomRange(0, items.size() - 1));
            }

            if (info.rpgData != null && info.favorRank != null) {
                info.rpgData.favor.afterLootingItems(info.player, info.favorRank.favor_drain_per_item, info, items.size());

                List<ItemStack> extraFavorItems = new ArrayList<ItemStack>();

                int extraTries = 0;

                while (extraFavorItems.size() < info.getExtraFavorItems()) {

                    extraTries++;
                    if (extraTries > 20) {
                        System.out.println("Tried to generate loot many times but failed! ");
                        break;
                    }
                    List<ItemStack> extra = populateOnce(info);
                    if (!extra.isEmpty()) {
                        extraFavorItems.add(RandomUtils.randomFromList(extra));
                    }
                }

                info.rpgData.favor.afterLootingItems(info.player, info.favorRank.extra_item_favor_cost, info, extraFavorItems.size());

                items.addAll(extraFavorItems);

            }

            items.forEach(x -> {
                ItemUtils.tryAnnounceItem(x, info.player);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private static List<ItemStack> populateOnce(LootInfo info) {
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (info == null) {
            return items;
        }

        try {
            items.addAll(new CurrencyLootGen(info).tryGenerate());
            items.addAll(new GearSoulLootGen(info).tryGenerate());
            items.addAll(new GemLootGen(info).tryGenerate());
            items.addAll(new RuneLootGen(info).tryGenerate());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

            if (Gear.has(stack)) {
                GearRarity rar = Gear.Load(stack)
                    .getRarity();
                if (rar.is_unique_item) {
                    SoundUtils.ding(victim.level, victim.blockPosition());
                }
            }

            victim.spawnAtLocation(stack, 1F);
        }
    }

}
