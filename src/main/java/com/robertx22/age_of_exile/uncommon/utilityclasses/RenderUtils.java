package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RenderUtils {

    public static void render16Icon(MatrixStack matrix, Identifier tex, int x, int y) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(tex);
        DrawableHelper.drawTexture(matrix, x, y, 0, 0, 16, 16, 16, 16);
    }

    public static void renderStack(ItemStack stack, int x, int y) {
        MinecraftClient mc = MinecraftClient.getInstance();
        RenderSystem.enableDepthTest();
        //  mc.getItemRenderer()
        //     .renderInGuiWithOverrides(mc.player, stack, x, y);
        //mc.getItemRenderer()
        //    .renderGuiItemOverlay(mc.textRenderer, stack, x, y, "");
        mc.getItemRenderer()
            .renderInGui(stack, x, y);
        RenderSystem.disableDepthTest();
    }
}