package com.robertx22.age_of_exile.gui.screens.player_skills;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PlayerSkillsScreen extends BaseScreen implements INamedScreen {
    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/skills/skills_background.png");

    static int sizeX = 176;
    static int sizeY = 207;

    MinecraftClient mc = MinecraftClient.getInstance();

    public PlayerSkillsScreen() {
        super(sizeX, sizeY);
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.PLAYER_SKILLS));
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/skills.png");
    }

    @Override
    public Words screenName() {
        return Words.Skills;
    }

    @Override
    public void init() {
        super.init();

        int num = 0;

        int x = guiLeft + 20;
        int y = guiTop + 50;

        for (PlayerSkill skill : SlashRegistry.PlayerSkills()
            .getList()) {

            addButton(new SkillButton(skill.type_enum,
                (int) (x + (num % 3) * BUTTON_SIZE_X * 1.75F),
                (int) (y + num / 3 * BUTTON_SIZE_Y * 1.75F)));

            num++;

        }

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(BACKGROUND);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    static int BUTTON_SIZE_X = 30;
    static int BUTTON_SIZE_Y = 30;

    public static void renderIconFor(MatrixStack matrix, PlayerSkillEnum skill, int x, int y, boolean renderText) {
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
            .bindTexture(SlashRegistry.PlayerSkills()
                .get(skill.id)
                .getIcon());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x + 7, y + 7, 16, 16, 16, 16, 16, 16);

        if (renderText) {
            int lvl = data.getLvl();
            int exp = data.getExp();
            int needed = data.getExpNeededToLevel();

            String lvltext = "Lvl: " + lvl;
            String xptext = exp + "/" + needed;
            String nametext = CLOC.translate(skill.word.locName());

            TextUtils.renderText(matrix, 1.25, nametext, x + BUTTON_SIZE_X / 2, y - 15, Formatting.GOLD);
            TextUtils.renderText(matrix, 0.8, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.2F), Formatting.YELLOW);
            TextUtils.renderText(matrix, 0.75, xptext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 1.5F), Formatting.GREEN);
        }
    }

    class SkillButton extends TexturedButtonWidget {

        PlayerSkillEnum skill;

        PlayerSkillData data;

        public SkillButton(PlayerSkillEnum se, int xPos, int yPos) {
            super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, Load.playerSkills(mc.player)
                .getBackGroundTextureFor(se), (button) -> {

            });
            this.skill = se;
            this.data = Load.playerSkills(mc.player)
                .getDataFor(skill);
        }

        @Override
        public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
            renderIconFor(matrix, skill, x, y, true);
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<Text> tooltip = new ArrayList<>();

                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }
}
