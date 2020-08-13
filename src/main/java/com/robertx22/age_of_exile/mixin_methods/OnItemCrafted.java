package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class OnItemCrafted {
    public static void onCraft(ItemStack stack, World world, PlayerEntity player, int amount, CallbackInfo ci) {

        if (world.isClient) {
            return;
        }

        if (CompatibleItemUtils.isCompatible(stack.getItem())) {
            EntityCap.UnitData data = Load.Unit(player);
            CompatibleItemUtils.tryCreateCompatibleItemStats(stack, data.getLevel(), player);
        }
    }
}
