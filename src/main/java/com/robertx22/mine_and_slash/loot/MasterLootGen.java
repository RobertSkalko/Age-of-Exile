package com.robertx22.mine_and_slash.loot;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.loot.generators.*;
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
            if (tries > 30) {
                System.out.println("Tried to generate loot many times but failed! " + info.toString());
                break;
            }

            List<ItemStack> extra = populateOnce(info);

            int missing = info.minItems - items.size();

            for (int i = 0; i < extra.size() && i < missing; i++) {
                items.add(extra.get(i));
            }

        }

        while (items.size() > info.maxItems) {
            items.remove(0);
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
        items.addAll(new CompatibleItemLootGen(info).tryGenerate());
        items.addAll(new SkillGemLootGen(info).tryGenerate());
        items.addAll(new JewelLootGen(info).tryGenerate());

        return items.stream()
            .filter(x -> x.isEmpty() == false)
            .collect(Collectors.toList());
    }

    public static List<ItemStack> generateLoot(UnitData mob, UnitData player,
                                               LivingEntity victim, PlayerEntity killer) {

        LootInfo info = LootInfo.ofMobKilled(killer, victim);
        List<ItemStack> items = generateLoot(info);

        return items;
    }

    public static void genAndDrop(UnitData mob, UnitData player, LivingEntity victim,
                                  PlayerEntity killer) {

        List<ItemStack> items = generateLoot(mob, player, victim, killer);

        for (ItemStack stack : items) {
            victim.dropStack(stack, 1F);
        }

    }

}
