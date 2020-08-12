package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.event_hooks.ontick.CompatibleItemInventoryCheck;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class OnItemCrafted {
    public static void onCraft(ItemStack stack, World world, PlayerEntity player, int amount, CallbackInfo ci) {

        if (world.isClient) {
            return;
        }

        if (CompatibleItemInventoryCheck.isComp(stack.getItem())) {
            EntityCap.UnitData data = Load.Unit(player);
            CompatibleItemInventoryCheck.tryCreateCompatibleItemStats(stack, data.getLevel(), player);
        }
    }
}
