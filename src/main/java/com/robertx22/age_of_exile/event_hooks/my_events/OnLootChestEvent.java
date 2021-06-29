package com.robertx22.age_of_exile.event_hooks.my_events;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OnLootChestEvent extends EventConsumer<ExileEvents.OnChestLooted> {

    @Override
    public void accept(ExileEvents.OnChestLooted event) {

        PlayerEntity player = event.player;

        LootInfo info = LootInfo.ofChestLoot(player, event.pos);

        if (WorldUtils.isDungeonWorld(player.world)) {
            if (Load.dungeonData(event.player.world).data.get(event.pos).data.failedOrEmpty()) {
                event.inventory.clear();
                // dont gen loot
                event.canceled = true;
                return;
            }
            info.multi += 10;
        }

        if (LootUtils.isMaxLevelDistancePenalty(info.level, Load.Unit(player)
            .getLevel())) {
            event.canceled = true;
            return;
        }

        Load.favor(player)
            .onOpenNewLootChest(info);

        List<ItemStack> items = MasterLootGen.generateLoot(info);

        List<Integer> list1 = mygetEmptySlotsRandomized(event.inventory, new Random());

        if (list1.isEmpty()) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            if (i < list1.size()) {
                int emptyslot = list1.get(i);
                event.inventory.setStack(emptyslot, items.get(i));
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

    @Override
    public int callOrder() {
        return -1;
    }
}
