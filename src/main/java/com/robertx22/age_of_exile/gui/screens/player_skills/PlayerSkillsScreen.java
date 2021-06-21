package com.robertx22.age_of_exile.gui.screens.player_skills;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.wiki.ExtraInfoButton;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PlayerSkillsScreen extends BaseScreen implements INamedScreen {
    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/skills/skills_background.png");

    static int sizeX = 256;
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

    PlayerSkill currentSkill;

    public void setCurrentSkill(PlayerSkill skill) {
        this.currentSkill = skill;

        this.init();

    }

    @Override
    public void init() {
        super.init();

        this.buttons.clear();

        int num = 0;

        int x = guiLeft + 10;
        int y = guiTop + 8;

        List<PlayerSkill> all = ExileDB.PlayerSkills()
            .getList();
        all.sort(Comparator.comparingInt(s -> s.order));

        int fitamount = this.width / (ProfessionButton.BUTTON_SIZE_X + 4);

        if (fitamount > all.size()) {
            fitamount = all.size();
        }
        int spaceLeft = sizeX - ((ProfessionButton.BUTTON_SIZE_X + 4) * fitamount);

        x += spaceLeft / 2;

        for (PlayerSkill skill : all) {

            if (currentSkill == null) {
                currentSkill = skill;
            }

            addButton(new ProfessionButton(this, skill,
                (int) (x + (num * (ProfessionButton.BUTTON_SIZE_X + 4))), y));
            num++;
        }

        if (currentSkill.type_enum.wiki != null) {
            addButton(new ExtraInfoButton(Arrays.asList(new LiteralText("Open Wiki")), guiLeft + 20, guiTop + sizeY - 50, a -> {
                WikiScreen wiki = new WikiScreen();
                wiki.changeType(currentSkill.type_enum.wiki);
                mc.openScreen(wiki);
            }));

        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static int BAR_X = 227;
    static int BAR_Y = 7;
    static Identifier BAR_TEX = new Identifier(Ref.MODID, "textures/gui/skills/bar.png");

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

        String nametext = CLOC.translate(Words.Professions.locName());

        //TextUtils.renderText(matrix, 1.5F, nametext, guiLeft + sizeX / 2, guiTop - 15, Formatting.GREEN);

        if (currentSkill != null) {

            String name = currentSkill.type_enum.word.translate() + " " + "(Level " + Load.playerSkills(mc.player)
                .getLevel(currentSkill.type_enum) + ")";
            renderTextAtMiddle(matrix, name, guiTop + 50, Formatting.GOLD);
            // renderTextAtMiddle(matrix,, guiTop + 65, Formatting.GREEN);

            List<OrderedText> list = mc.textRenderer.wrapLines(currentSkill.type_enum.desc.locName(), sizeX - 25);

            int ypos = guiTop + 65;

            for (OrderedText txt : list) {
                this.renderTextAtMiddle(matrix, txt, ypos, Formatting.WHITE);
                ypos += mc.textRenderer.fontHeight + 2;
            }

            renderBar(matrix);

        }

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    void renderBar(MatrixStack matrix) {
        float filledMulti = Load.playerSkills(mc.player)
            .getExpDividedByNeededToLevelMulti(currentSkill.type_enum);

        int y = guiTop + sizeY - 20;

        mc.getTextureManager()
            .bindTexture(BAR_TEX);
        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - BAR_X / 2,
            y, 0, 0, BAR_X, BAR_Y
        );

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - BAR_X / 2,
            y, 0, BAR_Y, (int) (BAR_X * filledMulti), BAR_Y
        );

        PlayerSkillData data = Load.playerSkills(mc.player)
            .getDataFor(currentSkill.type_enum);
        int exp = data.getExp();
        int needed = data.getExpNeededToLevel();
        String xptext = exp + "/" + needed;

        renderTextAtMiddle(matrix, xptext, y - 10, Formatting.YELLOW);

    }

    private void renderTextAtMiddle(MatrixStack matrix, String text, int y, Formatting format) {
        mc.textRenderer.drawWithShadow(matrix, text,
            guiLeft + sizeX / 2 - mc.textRenderer.getWidth(text) / 2, y
            , format.getColorValue());
    }

    private void renderTextAtMiddle(MatrixStack matrix, OrderedText text, int y, Formatting format) {
        mc.textRenderer.drawWithShadow(matrix, text,
            guiLeft + sizeX / 2 - mc.textRenderer.getWidth(text) / 2, y
            , format.getColorValue());
    }

    public enum IconRenderType {
        SCREEN, OVERLAY;
    }

}
