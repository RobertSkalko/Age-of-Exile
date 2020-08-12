package com.robertx22.age_of_exile.mixin_methods;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenChestLootMethod {

    public static void onLootGen(Inventory inventory, LootContext context, CallbackInfo ci) {
        BlockEntity chest = null;
        BlockPos pos = null;

        if (inventory instanceof BlockEntity) {
            chest = (BlockEntity) inventory;
        }
        World world = null;
        if (chest != null) {
            world = chest.getWorld();
            pos = chest.getPos();
        }

        if (world == null) {
            return;
        }

        MMORPG.mixinLog("Loottable isn't null");

        LootInfo info = LootInfo.ofBlockPosition(world, pos);
        info.isChestLoot = true;

        info.minItems = RandomUtils.RandomRange(1, 3);

        List<ItemStack> items = MasterLootGen.generateLoot(info);

        List<Integer> list1 = mygetEmptySlotsRandomized(inventory, new Random());

        for (int i = 0; i < items.size(); i++) {
            if (list1.size() >= i) {
                int emptyslot = list1.get(i);

                inventory.setStack(emptyslot, items.get(i));
            }
        }

    }

    private static List<Integer> mygetEmptySlotsRandomized(Inventory inventory, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.getStack(i)
                .isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }
}
