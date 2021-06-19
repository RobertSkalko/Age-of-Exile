package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.gui.overlays.AreaLevelIndicator;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import com.robertx22.library_of_exile.utils.CLOC;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class RPGGuiOverlay extends DrawableHelper implements HudRenderCallback {

    static Identifier BASETEX = new Identifier(Ref.MODID, "textures/gui/overlay/base.png");
    static Identifier MANA_RESERVE = new Identifier(Ref.MODID, "textures/gui/overlay/mana_reserve.png");

    public RPGGuiOverlay() {
        super();
    }

    static int BAR_HEIGHT = 9;

    public static int INNER_BAR_WIDTH = 78;
    static int INNER_BAR_HEIGHT = 5;

    MinecraftClient mc = MinecraftClient.getInstance();

    int ticks = 0;

    int areaLvlTicks = 0;
    int currentAreaLvl = 0;

    public static int BUTTON_SIZE_X = 30;
    public static int BUTTON_SIZE_Y = 30;

    public enum IconRenderType {
        SCREEN, OVERLAY;
    }

    public static void renderIconFor(MatrixStack matrix, PlayerSkillEnum skill, int x, int y, IconRenderType render) {
        // this is separated because it's used in 2 different places. The screen, and overlay

        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerSkillData data = Load.playerSkills(mc.player)
            .getDataFor(skill);

        mc.getTextureManager()
            .bindTexture(Load.playerSkills(mc.player)
                .getBackGroundTextureFor(skill));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x, y, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        mc.getTextureManager()
            .bindTexture(Database.PlayerSkills()
                .get(skill.id)
                .getIcon());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x + 7, y + 7, 16, 16, 16, 16, 16, 16);

        if (render == IconRenderType.OVERLAY || render == IconRenderType.SCREEN) {
            int lvl = data.getLvl();
            String lvltext = "Lvl: " + lvl;
            TextUtils.renderText(matrix, 1, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.2F), Formatting.YELLOW);
        }

        if (render == IconRenderType.SCREEN) {
            int exp = data.getExp();
            int needed = data.getExpNeededToLevel();

            String xptext = exp + "/" + needed;
            String nametext = CLOC.translate(skill.word.locName());

            TextUtils.renderText(matrix, 1, nametext, x + BUTTON_SIZE_X / 2, y - 5, Formatting.GOLD);
            TextUtils.renderText(matrix, 1, xptext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.55F), Formatting.GREEN);

        }
    }

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.player == null) {
                return;
            }
            if (mc.options.debugEnabled || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }

            if (ModConfig.get().client.PLAYER_GUI_TYPE == PlayerGUIs.NONE) {
                return;
            }

            if (SyncedToClientValues.skillJustLeveled != null) {
                if (SyncedToClientValues.ticksToShowSkillLvled > 0) {
                    SyncedToClientValues.ticksToShowSkillLvled--;
                    renderIconFor(matrix, SyncedToClientValues.skillJustLeveled, mc.getWindow()
                        .getScaledWidth() / 2 - BUTTON_SIZE_X / 2, 0, IconRenderType.OVERLAY);
                }
            }

            PlayerEntity en = mc.player;
            EntityCap.UnitData data = Load.Unit(en);

            if (data == null) {
                return;
            }

            ticks++;

            if (currentAreaLvl != SyncedToClientValues.areaLevel) {
                currentAreaLvl = SyncedToClientValues.areaLevel;
                areaLvlTicks = 200;
            }

            ModConfig.get().client.OVERLAY_BARS.parts.forEach(c -> {

                if (c.type.shouldRender(data, mc.player)) {
                    renderBar(c, c.type,
                        matrix,
                        c.getPosition(),
                        c.type.getText(data, mc.player),
                        false);
                }
            });

            ModConfig.get().client.OVERLAY_BARS.parts.forEach(c -> {
                if (c.type.shouldRender(data, mc.player)) {
                    renderBar(c, c.type,
                        matrix,
                        c.getPosition(),
                        c.type.getText(data, mc.player),
                        true);
                }
            });

            GuiPartConfig c = ModConfig.get().client.AREA_LVL_OVERLAY;

            if (c.enabled) {
                if (ModConfig.get().client.ALWAYS_RENDER_LEVEL_OVERLAY || areaLvlTicks > 0) {
                    areaLvlTicks--;
                    AreaLevelIndicator.draw(
                        matrix,
                        c.getPosition().x,
                        c.getPosition().y,
                        SyncedToClientValues.areaLevel
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void renderBar(GuiPartConfig config, BarGuiType type, MatrixStack matrix, PointData point, String text, boolean drawText) {

        if (!drawText) {
            mc.getTextureManager()
                .bindTexture(BASETEX);
            drawTexture(matrix, point.x, point.y, GuiConstants.BAR_WIDTH, BAR_HEIGHT, 0, 0, GuiConstants.BAR_WIDTH, BAR_HEIGHT, GuiConstants.BAR_WIDTH, BAR_HEIGHT);
            float multi = type.getMulti(Load.Unit(mc.player), mc.player);
            int bar = (int) (multi * INNER_BAR_WIDTH);
            mc.getTextureManager()
                .bindTexture(type.getTexture(Load.Unit(mc.player), mc.player));
            drawTexture(matrix, point.x + 2, point.y + 2, bar, INNER_BAR_HEIGHT, 0, 0, INNER_BAR_WIDTH, INNER_BAR_HEIGHT, INNER_BAR_WIDTH, INNER_BAR_HEIGHT);

            if (config.icon_renderer == GuiPartConfig.IconRenderer.LEFT) {
                mc.getTextureManager()
                    .bindTexture(type.getIcon(Load.Unit(mc.player), mc.player));
                drawTexture(matrix, point.x - 10, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            } else if (config.icon_renderer == GuiPartConfig.IconRenderer.RIGHT) {
                mc.getTextureManager()
                    .bindTexture(type.getIcon(Load.Unit(mc.player), mc.player));
                drawTexture(matrix, point.x + GuiConstants.BAR_WIDTH + 1, point.y, 9, 9, 0, 0, 9, 9, 9, 9); // draw icon
            }

            if (config.type == BarGuiType.MANA) {
                float reserved = Load.spells(mc.player)
                    .getManaReservedByAuras(); // todo cache this
                mc.getTextureManager()
                    .bindTexture(MANA_RESERVE);

                int width = (int) (INNER_BAR_WIDTH * reserved);
                drawTexture(matrix, point.x + 2 + (INNER_BAR_WIDTH - width), point.y + 2, width, INNER_BAR_HEIGHT, 0, 0, INNER_BAR_WIDTH, INNER_BAR_HEIGHT, INNER_BAR_WIDTH, INNER_BAR_HEIGHT);
            }
        }

        if (drawText) {

            double scale = 0.7;

            int width = mc.textRenderer.getWidth(text);
            int textX = (int) (point.x - width / 2F + GuiConstants.BAR_WIDTH / 2F);
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