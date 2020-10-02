package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PlayerGuiOverlay extends DrawableHelper implements HudRenderCallback {

    public PlayerGuiOverlay() {
        super();
    }

    static int BAR_WIDTH = 116;
    static int XP_BAR_WIDTH = 38;

    Identifier TEX = new Identifier(Ref.MODID, "textures/gui/playergui.png");

    MinecraftClient mc = MinecraftClient.getInstance();

    int ticks = 0;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        if (mc.player == null) {
            return;
        }
        try {

            PlayerEntity en = mc.player;
            UnitData data = Load.Unit(en);

            if (en.isSpectator()) {
                return;
            }

            if (en == null || data == null) {
                return;
            }

            ticks++;

            int x = 5;
            int y = 5;

            int curhp = (int) (mc.player.getHealth() + data.getResources()
                .getMagicShield());
            int maxhp = (int) (mc.player.getMaxHealth() + data.getUnit()
                .magicShieldData()
                .getAverageValue());

            SpellSchool school = Load.perks(mc.player)
                .getMainSpellSchool();
            if (school != null) {
                mc.getTextureManager()
                    .bindTexture(new Identifier(school.icon));
                drawTexture(matrix, x + 5, y + 5, 0, 0, 32, 32, 32, 32);
            }

            mc.getTextureManager()
                .bindTexture(TEX);
            drawTexture(matrix, x, y, 0, 0, 158, 49);

            float hpmulti = (float) curhp / (float) maxhp;
            float manamulti = data.getCurrentMana() / data.getUnit()
                .manaData()
                .getAverageValue();
            float xpmulti = (float) data.getExp() / (float) data.getExpRequiredForLevelUp();

            int hpbar = (int) (hpmulti * BAR_WIDTH);
            int manabar = (int) (manamulti * BAR_WIDTH);
            int xpbar = (int) (xpmulti * XP_BAR_WIDTH);

            drawTexture(matrix, x + 42, y + 7, 0, 59, hpbar, 13);
            drawTexture(matrix, x + 42, y + 23, 0, 75, manabar, 13);
            drawTexture(matrix, x + 2, y + 42, 0, 96, xpbar, 5);

            String text = curhp + "/" + maxhp;
            int width = mc.textRenderer.getWidth(text);
            float hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            float hpy = 9 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.WHITE.getColorValue());

            text = (int) data.getCurrentMana() + "/" + (int) data.getUnit()
                .manaData()
                .getAverageValue();
            width = mc.textRenderer.getWidth(text);
            hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            hpy = 25 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.WHITE.getColorValue());

            text = data.getLevel() + "";
            width = mc.textRenderer.getWidth(text);
            hpx = x + 21 - width / 2F;
            hpy = y + 27;
            // mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.GREEN.getColorValue());

            drawPrettyText(text, matrix, hpx, hpy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawPrettyText(String string, MatrixStack matrix, float m, float n) {
// copied from how vanilla renders the total experience level text
        mc.textRenderer
            .draw(matrix, string, (m + 1), n, 0);
        mc.textRenderer
            .draw(matrix, string, (m - 1), n, 0);
        mc.textRenderer
            .draw(matrix, string, m, (n + 1), 0);
        mc.textRenderer
            .draw(matrix, string, m, (n - 1), 0);
        mc.textRenderer
            .draw(matrix, string, m, n, 8453920);
    }

}