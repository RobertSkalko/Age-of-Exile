package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class HealthReplaceGuiOverlay extends DrawableHelper implements HudRenderCallback {

    public HealthReplaceGuiOverlay() {
        super();
    }

    public static int BAR_WIDTH = 82;
    static int BAR_HEIGHT = 9;

    public static int INNER_BAR_WIDTH = 78;
    static int INNER_BAR_HEIGHT = 5;

    MinecraftClient mc = MinecraftClient.getInstance();

    int ticks = 0;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.player == null) {
                return;
            }
            if (mc.options.debugEnabled || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }

            if (!ModConfig.get().client.PLAYER_GUI_TYPE.equals(PlayerGUIs.REPLACE_VANILLA)) {
                return;
            }

            PlayerEntity en = mc.player;
            EntityCap.UnitData data = Load.Unit(en);

            if (data == null) {
                return;
            }

            ticks++;

            ModConfig.get().client.OVERLAY_GUI.parts.forEach(c -> {

                if (c.type.shouldRender(data, mc.player)) {
                    renderBar(c.type,
                        matrix,
                        c.getPosition(),
                        c.type.getText(data, mc.player),
                        false);
                }
            });

            ModConfig.get().client.OVERLAY_GUI.parts.forEach(c -> {
                if (c.type.shouldRender(data, mc.player)) {
                    renderBar(c.type,
                        matrix,
                        c.getPosition(),
                        c.type.getText(data, mc.player),
                        true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Identifier BASETEX = new Identifier(Ref.MODID, "textures/gui/overlay/base.png");

    void renderBar(BarGuiType type, MatrixStack matrix, PointData point, String text, boolean drawText) {

        if (!drawText) {
            mc.getTextureManager()
                .bindTexture(BASETEX);
            drawTexture(matrix, point.x, point.y, BAR_WIDTH, BAR_HEIGHT, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
            float multi = type.getMulti(Load.Unit(mc.player), mc.player);
            int bar = (int) (multi * INNER_BAR_WIDTH);
            mc.getTextureManager()
                .bindTexture(type.getTexture(Load.Unit(mc.player), mc.player));
            drawTexture(matrix, point.x + 2, point.y + 2, bar, INNER_BAR_HEIGHT, 0, 0, INNER_BAR_WIDTH, INNER_BAR_HEIGHT, INNER_BAR_WIDTH, INNER_BAR_HEIGHT);
        }

        if (drawText) {

            double scale = 0.7;

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