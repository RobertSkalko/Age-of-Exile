package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class OnItemsClicked {
    public static void test(PlayerInventory inv, int slot, ItemStack stack, CallbackInfo ci) {
        ItemStack stackatslot = inv.getStack(slot);

        if (stack.getItem() == Items.DIAMOND) {

            ICommonDataItem data = ICommonDataItem.load(stackatslot);
            if (data != null) {
                int dmg = stackatslot.getDamage() - 100;
                if (dmg < 0) {
                    dmg = 0;
                }
                stackatslot.setDamage(dmg);
                stack.decrement(1);

                // ci.cancel();

            }

        }

    }
}
