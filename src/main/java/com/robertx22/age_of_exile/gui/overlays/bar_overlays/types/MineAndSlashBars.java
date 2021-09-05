package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class MineAndSlashBars extends DrawableHelper {
    static Identifier BASETEX = new Identifier(Ref.MODID, "textures/gui/overlay/base.png");

    static int BAR_HEIGHT = 11;
    static int BAR_WIDTH = 107;
    public static int INNER_BAR_WIDTH = 101;
    static int INNER_BAR_HEIGHT = 5;

    static MinecraftClient mc = MinecraftClient.getInstance();

    static void renderMineAndSlashBar(GuiPartConfig config, BarGuiType type, MatrixStack matrix, PointData point, String text, boolean drawText) {

        if (!drawText) {
            mc.getTextureManager()
                .bindTexture(BASETEX);
            drawTexture(matrix, point.x, point.y, BAR_WIDTH, BAR_HEIGHT, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
            float multi = type.getMulti(Load.Unit(mc.player), mc.player);
            int bar = (int) (multi * INNER_BAR_WIDTH);
            mc.getTextureManager()
                .bindTexture(type.getTexture(Load.Unit(mc.player), mc.player));
            drawTexture(matrix, point.x + 2, point.y + 2, bar, INNER_BAR_HEIGHT, 0, 0, bar, INNER_BAR_HEIGHT, INNER_BAR_WIDTH, INNER_BAR_HEIGHT);

            if (config.icon_renderer == GuiPartConfig.IconRenderer.LEFT) {
                mc.getTextureManager()
                    .bindTexture(type.getIcon(Load.Unit(mc.player), mc.player));
                drawTexture(matrix, point.x - 10, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            } else if (config.icon_renderer == GuiPartConfig.IconRenderer.RIGHT) {
                mc.getTextureManager()
                    .bindTexture(type.getIcon(Load.Unit(mc.player), mc.player));
                drawTexture(matrix, point.x + BAR_WIDTH + 1, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            }

        }

        if (drawText) {

            double scale = 0.9D;

            int width = mc.textRenderer.getWidth(text);
            int textX = (int) (point.x - width / 2F + BAR_WIDTH / 2F);
            int textY = point.y + 2 + 3;

            double antiScale = 1.0D / scale;

            RenderSystem.scaled(scale, scale, scale);
            double textWidthMinus = (width * antiScale / 2) - width / 2F;// fixed the centering with this!!!
            double textHeightMinus = 9.0D * scale / 2.0D;
            float xp = (float) ((double) textX + textWidthMinus);
            float yp = (float) ((double) textY - textHeightMinus);
            float xf = (float) ((double) xp * antiScale);
            float yf = (float) ((double) yp * antiScale);

            mc.textRenderer.drawWithShadow(matrix, text, xf, yf, Formatting.WHITE.getColorValue());
            RenderSystem.scaled(antiScale, antiScale, antiScale);
        }
    }
}
