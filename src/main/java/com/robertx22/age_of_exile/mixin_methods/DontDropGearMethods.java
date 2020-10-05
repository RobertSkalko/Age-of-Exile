package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class DontDropGearMethods {
    public static void on(PlayerInventory inv, CallbackInfo ci) {

        if (!ModConfig.get().Server.SAVE_GEAR_AND_HOTBAR_ON_DEATH) {
            return;
        }

        try {

            for (int i = 0; i < inv.main.size(); i++) {

                if (i < 9) {
                    continue; // dont drop hotbar.
                }
                ItemStack stack = inv.main.get(i);

                if (!stack.isEmpty()) {
                    inv.player.dropItem(stack, true, false);
                    inv.main.set(i, ItemStack.EMPTY); // idk why this is needed but otherwise it dupes some items
                }
            }

            ci.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}