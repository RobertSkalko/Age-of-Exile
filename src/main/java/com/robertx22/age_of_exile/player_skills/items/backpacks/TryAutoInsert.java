package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;

public class TryAutoInsert {

    public static void run(IInventory inv, ItemStack stack) {

        // try put into auto bags
        for (int i = 0; i < inv.getContainerSize(); i++) {

            if (inv.getItem(i)
                .getItem() instanceof BackpackItem) {

                ItemStack backpack = inv.getItem(i);

                if (backpack
                    .equals(inv.player.getMainHandItem())) {
                    continue; // if holding, dont put
                }

                BackpackInfo info = BackpackContainer.getInfo(inv.player, backpack);

                if (info.autoPickups.stream()
                    .noneMatch(x -> x.autoPicksUp(stack))) {
                    continue;
                }

                BackpackInventory binv = new BackpackInventory(inv.player, backpack);

                if (binv.addItem(stack)
                    .isEmpty()) {
                    stack.setCount(0);
                    binv.writeItemStack();
                    SoundUtils.playSound(inv.player, SoundEvents.ITEM_PICKUP, 1, 1);
                    return;
                }
            }

        }
    }
}
