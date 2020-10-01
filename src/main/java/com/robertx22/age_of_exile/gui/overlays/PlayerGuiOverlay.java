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

            int hpbar = (int) (hpmulti * BAR_WIDTH);
            int manabar = (int) (manamulti * BAR_WIDTH);

            drawTexture(matrix, x + 42, y + 7, 0, 59, hpbar, 13);
            drawTexture(matrix, x + 42, y + 23, 0, 75, manabar, 13);

            String name = curhp + "/" + maxhp;
            int width = mc.textRenderer.getWidth(name);
            float hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            float hpy = 9 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, name, hpx, hpy, Formatting.WHITE.getColorValue());

            name = (int) data.getCurrentMana() + "/" + (int) data.getUnit()
                .manaData()
                .getAverageValue();
            width = mc.textRenderer.getWidth(name);
            hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            hpy = 25 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, name, hpx, hpy, Formatting.WHITE.getColorValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}