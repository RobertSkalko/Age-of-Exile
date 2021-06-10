package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.RenderSpellIcon;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "RETURN"))
    private void drawOnTopMine(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci) {

        ItemRenderer ire = (ItemRenderer) (Object) this;
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.0D, 0.0D, (double) (ire.zOffset + 200.0F));

        RenderSpellIcon.drawMyGlintEnd(matrixStack, x, y, stack);
    }
}
