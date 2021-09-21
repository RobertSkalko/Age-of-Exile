package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class RPGGuiOverlay extends AbstractGui {

    static ResourceLocation BASETEX = new ResourceLocation(SlashRef.MODID, "textures/gui/overlay/base.png");
    static ResourceLocation MANA_RESERVE = new ResourceLocation(SlashRef.MODID, "textures/gui/overlay/mana_reserve.png");

    public RPGGuiOverlay() {
        super();
    }

    static int BAR_HEIGHT = 9;
    public static int INNER_BAR_WIDTH = 78;
    static int INNER_BAR_HEIGHT = 5;

    Minecraft mc = Minecraft.getInstance();

    int areaLvlTicks = 0;
    int currentAreaLvl = 0;

    public static int BUTTON_SIZE_X = 30;
    public static int BUTTON_SIZE_Y = 30;

    public enum IconRenderType {
        SCREEN, OVERLAY;
    }

    public static void renderIconFor(MatrixStack matrix, PlayerSkillEnum skill, int x, int y, IconRenderType render) {
        // this is separated because it's used in 2 different places. The screen, and overlay

        Minecraft mc = Minecraft.getInstance();
        PlayerSkillData data = Load.playerRPGData(mc.player).professions
            .getDataFor(skill);

        mc.getTextureManager()
            .bind(Load.playerRPGData(mc.player).professions
                .getBackGroundTextureFor(skill));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, x, y, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        mc.getTextureManager()
            .bind(ExileDB.PlayerSkills()
                .get(skill.id)
                .getIcon());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, x + 7, y + 7, 16, 16, 16, 16, 16, 16);

        if (render == IconRenderType.OVERLAY || render == IconRenderType.SCREEN) {
            int lvl = data.getLvl();
            String lvltext = "Lvl: " + lvl;
            TextUtils.renderText(matrix, 1, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.2F), TextFormatting.YELLOW);
        }

        if (render == IconRenderType.SCREEN) {
            int exp = data.getExp();
            int needed = data.getExpNeededToLevel();

            String xptext = exp + "/" + needed;
            String nametext = CLOC.translate(skill.word.locName());

            TextUtils.renderText(matrix, 1, nametext, x + BUTTON_SIZE_X / 2, y - 5, TextFormatting.GOLD);
            TextUtils.renderText(matrix, 1, xptext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.55F), TextFormatting.GREEN);

        }
    }

    public void onHudRender(MatrixStack matrix, float v) {

        try {

            if (mc.player == null) {
                return;
            }
            if (mc.options.renderDebug || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }

            if (ModConfig.get().client.PLAYER_GUI_TYPE == PlayerGUIs.NONE) {
                return;
            }

            if (SyncedToClientValues.skillJustLeveled != null) {
                if (SyncedToClientValues.ticksToShowSkillLvled > 0) {
                    SyncedToClientValues.ticksToShowSkillLvled--;
                    renderIconFor(matrix, SyncedToClientValues.skillJustLeveled, mc.getWindow()
                        .getGuiScaledWidth() / 2 - BUTTON_SIZE_X / 2, 0, IconRenderType.OVERLAY);
                }
            }

            PlayerEntity en = mc.player;
            EntityData data = Load.Unit(en);

            if (data == null) {
                return;
            }

            if (currentAreaLvl != SyncedToClientValues.areaLevel) {
                currentAreaLvl = SyncedToClientValues.areaLevel;
                areaLvlTicks = 200;
            }

            OverlayTypes.map.get(ModConfig.get().client.GUI_POSITION)
                .forEach(c -> {

                    if (c.type.shouldRender(data, mc.player)) {
                        MineAndSlashBars.renderMineAndSlashBar(c, c.type,
                            matrix,
                            c.getPosition(),
                            c.type.getText(data, mc.player),
                            false);
                    }

                    if (c.type.shouldRender(data, mc.player)) {
                        MineAndSlashBars.renderMineAndSlashBar(c, c.type,
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

}