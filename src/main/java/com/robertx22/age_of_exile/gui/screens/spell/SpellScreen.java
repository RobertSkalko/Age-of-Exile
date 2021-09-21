package com.robertx22.age_of_exile.gui.screens.spell;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.synergy.Synergy;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class SpellScreen extends BaseScreen implements INamedScreen, ILeftRight {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/spells/spell_school_background.png");

    static int sizeX = 250;
    static int sizeY = 233;
    public static boolean IS_PICKING_HOTBAR_SPELL = false;
    public static int NUMBER_OF_HOTBAR_FOR_PICKING = 0;

    Minecraft mc = Minecraft.getInstance();

    public List<SpellSchool> schoolsInOrder = ExileDB.SpellSchools()
        .getList();
    public int currentIndex = 0;
    public int maxIndex = ExileDB.SpellSchools()
        .getSize() - 1;

    public SpellSchool currentSchool() {
        return schoolsInOrder.get(currentIndex);
    }

    public SpellScreen() {
        super(sizeX, sizeY);
        IS_PICKING_HOTBAR_SPELL = false;
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/spells.png");
    }

    @Override
    public Words screenName() {
        return Words.Skills;
    }

    static int SLOT_SPACING = 21;

    @Override
    public void init() {
        super.init();

        try {
            this.buttons.clear();
            this.children.clear();

            addButton(new LeftRightButton(this, guiLeft + 100 - LeftRightButton.xSize - 5, guiTop + 25 - LeftRightButton.ySize / 2, true));
            addButton(new LeftRightButton(this, guiLeft + 150 + 5, guiTop + 25 - LeftRightButton.ySize / 2, false));

            currentSchool().spells.entrySet()
                .forEach(e -> {

                    PointData point = e.getValue();
                    Spell spell = ExileDB.Spells()
                        .get(e.getKey());

                    if (spell != null) {
                        int x = this.guiLeft + 12 + (point.x * SLOT_SPACING);
                        int y = this.guiTop + 177 - (point.y * SLOT_SPACING);

                        this.addButton(new LearnSpellButton(this, spell, x, y));
                    }
                });

            currentSchool().synergies.entrySet()
                .forEach(e -> {

                    PointData point = e.getValue();
                    Synergy synergy = ExileDB.Synergies()
                        .get(e.getKey());

                    if (synergy != null) {

                        int x = this.guiLeft + 12 + (point.x * SLOT_SPACING);
                        int y = this.guiTop + 177 - (point.y * SLOT_SPACING);

                        this.addButton(new LearnSynergyButton(this, synergy, x, y));
                    }
                });

            for (int i = 0; i < 8; i++) {

                int x = guiLeft + 10 + (PickHotbarButton.BUTTON_SIZE_X * i);

                if (i > 3) {
                    x += 70;
                }
                int y = guiTop + 204;

                this.addButton(new PickHotbarButton(this, i, x, y));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {
            mc.getTextureManager()
                .bind(BACKGROUND);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            blit(matrix, mc.getWindow()
                    .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                    .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
            );

            mc.getTextureManager()
                .bind(currentSchool().getIconLoc());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            blit(matrix, guiLeft + 108, guiTop + 8, 34, 34, 34, 34, 34, 34);

            // background
            mc.getTextureManager()
                .bind(currentSchool().getBackgroundLoc());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            blit(matrix, guiLeft + 7, guiTop + 7, 93, 36, 93, 36, 93, 36);
            blit(matrix, guiLeft + 150, guiTop + 7, 93, 36, 93, 36, 93, 36);

            super.render(matrix, x, y, ticks);

            String txt = "Points: " + Load.spells(mc.player)
                .getFreeSpellPoints();
            GuiUtils.renderScaledText(matrix, guiLeft + 125, guiTop + 215, 1D, txt, TextFormatting.GREEN);

            buttons.forEach(b -> b.renderToolTip(matrix, x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goLeft() {
        this.currentIndex--;
        if (currentIndex < 0) {
            currentIndex = maxIndex;
        }
        init();
    }

    @Override
    public void goRight() {
        currentIndex++;
        if (currentIndex > maxIndex) {
            currentIndex = 0;
        }
        init();
    }
}