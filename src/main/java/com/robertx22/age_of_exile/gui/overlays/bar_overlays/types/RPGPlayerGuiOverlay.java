package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RPGPlayerGuiOverlay extends DrawableHelper implements HudRenderCallback {

    public RPGPlayerGuiOverlay() {
        super();
    }

    static int BAR_WIDTH = 116;
    static int XP_BAR_WIDTH = 38;

    Identifier TEX = new Identifier(Ref.MODID, "textures/gui/playergui.png");
    Identifier NO_SCHOOL_TEX = new Identifier(Ref.MODID, "textures/gui/no_school.png");

    MinecraftClient mc = MinecraftClient.getInstance();

    int ticks = 0;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        if (mc.player == null) {
            return;
        }
        if (mc.options.debugEnabled) {
            return; // dont render on debug screen
        }
        try {
            if (!ModConfig.get().client.PLAYER_GUI_TYPE.equals(PlayerGUIs.RPG)) {
                return;
            }

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

            int curMana = (int) data.getResources()
                .getMana();
            int maxMana = (int) data.getUnit()
                .manaData()
                .getAverageValue();

            SpellSchool school = Load.perks(mc.player)
                .getMainSpellSchool();
            if (school != null) {
                mc.getTextureManager()
                    .bindTexture(new Identifier(school.icon));
            } else {
                mc.getTextureManager()
                    .bindTexture(NO_SCHOOL_TEX);
            }
            drawTexture(matrix, x + 5, y + 5, 0, 0, 32, 32, 32, 32);

            mc.getTextureManager()
                .bindTexture(TEX);
            drawTexture(matrix, x, y, 0, 0, 158, 49);

            float hpmulti = (float) curhp / (float) maxhp;
            float manamulti = (float) curMana / (float) maxMana;

            if (data.getUnit()
                .getCalculatedStat(Blood.getInstance())
                .getAverageValue() > data.getUnit()
                .manaData()
                .getAverageValue()) {
                manamulti = data.getResources()
                    .getBlood() / data.getUnit()
                    .getCalculatedStat(Blood.getInstance())
                    .getAverageValue();
            }

            float xpmulti = (float) data.getExp() / (float) data.getExpRequiredForLevelUp();

            hpmulti = MathHelper.clamp(hpmulti, 0, 1);
            manamulti = MathHelper.clamp(manamulti, 0, 1);
            xpmulti = MathHelper.clamp(xpmulti, 0, 1);

            int hpbar = (int) (hpmulti * BAR_WIDTH);
            int manabar = (int) (manamulti * BAR_WIDTH);
            int xpbar = (int) (xpmulti * XP_BAR_WIDTH);

            int hpYPos = data.getUnit()
                .magicShieldData()
                .getAverageValue() > mc.player.getMaxHealth() ? 142 : 59;

            drawTexture(matrix, x + 42, y + 7, 0, hpYPos, hpbar, 13);
            drawTexture(matrix, x + 42, y + 23, 0, 75, manabar, 13);
            drawTexture(matrix, x + 2, y + 42, 0, 96, xpbar, 5);

            String text = curhp + "/" + maxhp;
            int width = mc.textRenderer.getWidth(text);
            float hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            float hpy = 9 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.WHITE.getColorValue());

            text = curMana + "/" + (int) maxMana;
            width = mc.textRenderer.getWidth(text);
            hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            hpy = 25 + 11 / 2F;
            mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.WHITE.getColorValue());

            text = data.getLevel() + "";
            width = mc.textRenderer.getWidth(text);
            hpx = x + 21 - width / 2F;
            hpy = y + 27;
            // mc.textRenderer.drawWithShadow(matrix, text, hpx, hpy, Formatting.GREEN.getColorValue());

            drawPrettyLevelText(text, matrix, hpx, hpy, TextColors.GREEN);

            hpx = x - width / 2F + 42 + BAR_WIDTH / 2F;
            text = "Area Level: ";

            String lvltext = SyncedToClientValues.areaLevel + "";

            int arealvl = SyncedToClientValues.areaLevel;
            TextColors color = TextColors.GREEN;

            int playerlvl = Load.Unit(mc.player)
                .getLevel();

            if (arealvl - 10 > playerlvl) {
                color = TextColors.RED;
                lvltext += "!!";
            } else if (arealvl - 5 > playerlvl) {
                color = TextColors.YELLOW;
                lvltext += "!";
            }
            text += lvltext;

            width = mc.textRenderer.getWidth(text);

            drawPrettyLevelText(text, matrix, hpx - width / 2F, hpy + 12, color);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum TextColors {
        RED(13313553),
        YELLOW(15252255),
        GREEN(8453920);
        int color;

        TextColors(int color) {
            this.color = color;
        }
    }

    public void drawPrettyLevelText(String string, MatrixStack matrix, float m, float n, TextColors color) {

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
            .draw(matrix, string, m, n, color.color);
    }

}