package com.robertx22.mine_and_slash.gui.overlays.spell_cast_bar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.util.Identifier;

public class SpellCastBarOverlay extends DrawableHelper implements HudRenderCallback {

    private static final Identifier GUI_BARS_TEXTURES = new Identifier("textures/gui/bars.png");

    static int WIDTH = 182;
    static int HEIGHT = 5;

    MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        PlayerSpellCap.ISpellsCap data = Load.spells(mc.player);

        if (data.getCastingData()
            .isCasting()) {

            int x = mc.getWindow()
                .getScaledWidth() / 2 - WIDTH / 2;
            int y = (int) (mc.getWindow()
                .getScaledHeight() / 1.25F - HEIGHT / 2);

            float percent =
                ((float) data.getCastingData().lastSpellCastTimeInTicks - (float) data.getCastingData().castingTicksLeft) / (float) data
                    .getCastingData().lastSpellCastTimeInTicks;

            render(matrix, x, y, BossBar.Color.PURPLE, BossBar.Style.NOTCHED_20, percent);
        }
    }

    private void render(MatrixStack matrix, int x, int y, BossBar.Color color, BossBar.Style overlay, float percent) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bindTexture(GUI_BARS_TEXTURES);

        drawTexture(matrix, x, y, 0, color.ordinal() * 5 * 2, WIDTH, HEIGHT);
        if (overlay != BossBar.Style.PROGRESS) {
            drawTexture(matrix, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2, WIDTH, HEIGHT);
        }

        int i = (int) (percent * 183.0F);
        if (i > 0) {
            drawTexture(matrix, x, y, 0, color.ordinal() * 5 * 2 + 5, i, HEIGHT);
            if (overlay != BossBar.Style.PROGRESS) {
                drawTexture(matrix, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2 + 5, i, HEIGHT);
            }
        }

    }

}
