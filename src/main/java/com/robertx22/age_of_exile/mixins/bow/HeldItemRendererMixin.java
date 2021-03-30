package com.robertx22.age_of_exile.mixins.bow;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ModBowItem;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    // Make sure that the custom items are rendered in the correct place based on the current swing progress of the hand
    @Redirect(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0))
    private Item renderItem(ItemStack heldItem) {
        return heldItem.getItem() instanceof ModBowItem ? Items.BOW : heldItem.getItem(); // return bow for rendering
    }

}
