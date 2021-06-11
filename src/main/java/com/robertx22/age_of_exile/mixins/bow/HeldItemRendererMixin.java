package com.robertx22.age_of_exile.mixins.bow;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.BowWeapon;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @ModifyVariable(
        method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
        at = @At(value = "STORE", ordinal = 0),
        ordinal = 0
    )
    private ItemStack modifyStack(ItemStack original) {
        return original.getItem() instanceof BowWeapon ? new ItemStack(Items.BOW) : original;
    }
}
