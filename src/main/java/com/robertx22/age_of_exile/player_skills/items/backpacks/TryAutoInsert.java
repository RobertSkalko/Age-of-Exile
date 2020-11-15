package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

public class TryAutoInsert {

    public static void run(PlayerInventory inv, ItemStack stack) {

        // try put into auto bags
        for (int i = 0; i < inv.size(); i++) {

            if (inv.getStack(i)
                .getItem() instanceof BackpackItem) {

                ItemStack backpack = inv.getStack(i);

                if (backpack
                    .equals(inv.player.getMainHandStack())) {
                    continue; // if holding, dont put
                }
                BackpackItem pack = (BackpackItem) backpack
                    .getItem();

                if (!pack.type.autoPicksUp()) {
                    continue;
                }

                if (pack.type.canAcceptStack(stack)) {
                    BackpackInventory binv = new BackpackInventory(backpack);
                    if (binv.canInsert(stack)) {
                        binv.addStack(stack);
                        stack.setCount(0);
                        binv.writeItemStack();
                        SoundUtils.playSound(inv.player, SoundEvents.ENTITY_ITEM_PICKUP, 1, 1);
                        return;
                    }
                }

            }

        }
    }
}
