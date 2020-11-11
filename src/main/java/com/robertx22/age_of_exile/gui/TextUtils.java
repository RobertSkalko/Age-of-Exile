package com.robertx22.age_of_exile.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

public class TextUtils {

    public static void renderText(MatrixStack matrix, double scale, String text, int x, int y, Formatting color) {
        MinecraftClient mc = MinecraftClient.getInstance();

        int width = mc.textRenderer.getWidth(text);
        int textX = (int) (x - width / 2F);
        int textY = y;

        double antiScale = 1.0D / scale;

        RenderSystem.scaled(scale, scale, scale);
        double textWidthMinus = (width * antiScale / 2) - width / 2F;// fixed the centering with this!!!
        double textHeightMinus = 9.0D * scale / 2.0D;
        float xp = (float) ((double) textX + textWidthMinus);
        float yp = (float) ((double) textY - textHeightMinus);
        float xf = (float) ((double) xp * antiScale);
        float yf = (float) ((double) yp * antiScale);

        mc.textRenderer.drawWithShadow(matrix, text, xf, yf, color.getColorValue());
        RenderSystem.scaled(antiScale, antiScale, antiScale);
    }

}
