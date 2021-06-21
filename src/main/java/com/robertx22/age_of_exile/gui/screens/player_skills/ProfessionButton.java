package com.robertx22.age_of_exile.gui.screens.player_skills;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ProfessionButton extends TexturedButtonWidget {

    PlayerSkillEnum skill;

    PlayerSkillData data;

    public static int BUTTON_SIZE_X = 30;
    public static int BUTTON_SIZE_Y = 30;

    MinecraftClient mc = MinecraftClient.getInstance();
    PlayerSkillsScreen screen;

    public ProfessionButton(PlayerSkillsScreen screen, PlayerSkill se, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y,
            Load.playerSkills(MinecraftClient.getInstance().player)
                .getBackGroundTextureFor(se.type_enum), (button) -> {
                screen.setCurrentSkill(se);

            });
        this.screen = screen;
        this.skill = se.type_enum;
        this.data = Load.playerSkills(mc.player)
            .getDataFor(skill);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        renderIconFor(matrix, skill, x, y, PlayerSkillsScreen.IconRenderType.SCREEN);
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {

            List<Text> tooltip = new ArrayList<>();

            TooltipInfo info = new TooltipInfo(mc.player);

            tooltip.addAll(ExileDB.PlayerSkills()
                .get(skill.id)
                .GetTooltipString(info));

            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

    public static void renderIconFor(MatrixStack matrix, PlayerSkillEnum skill, int x, int y, PlayerSkillsScreen.IconRenderType render) {
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
            .bindTexture(ExileDB.PlayerSkills()
                .get(skill.id)
                .getIcon());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x + 7, y + 7, 16, 16, 16, 16, 16, 16);

        if (render == PlayerSkillsScreen.IconRenderType.OVERLAY || render == PlayerSkillsScreen.IconRenderType.SCREEN) {
            int lvl = data.getLvl();
            String lvltext = "" + lvl;
            TextUtils.renderText(matrix, 0.8F, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 0.85F), Formatting.YELLOW);
        }

        if (render == PlayerSkillsScreen.IconRenderType.SCREEN) {
            int exp = data.getExp();
            int needed = data.getExpNeededToLevel();

            String xptext = exp + "/" + needed;
            String nametext = CLOC.translate(skill.word.locName());

            //TextUtils.renderText(matrix, 1, nametext, x + BUTTON_SIZE_X / 2, y - 1, Formatting.GOLD);
            //TextUtils.renderText(matrix, 1, xptext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.55F), Formatting.GREEN);

        }
    }
}
