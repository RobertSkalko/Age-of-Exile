package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import javafx.geometry.Point2D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SkillTreeScreen extends BaseScreen {

    Identifier BACKGROUND_TEXTURE = new Identifier(
        Ref.MODID, "textures/gui/skill_tree/water.png");

    public SkillTreeScreen() {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
    }

    HashMap<AbstractButtonWidget, Point2D> originalButtonLocMap = new HashMap<>();

    MinecraftClient mc = MinecraftClient.getInstance();

    SpellSchool school;

    @Override
    protected void init() {
        super.init();

        List<SpellSchool> list = SlashRegistry.SpellSchools()
            .getList();
        list.sort(Comparator.comparingInt(x -> x.order));
        this.school = list.get(0);

        refreshButtons();

    }

    public void refreshButtons() {
        this.scrollX = 0;
        this.scrollY = 0;

        school.calcData.perks.entrySet()
            .forEach(e -> {

                // centers them if they are smaller than the biggest one
                int addx = (e.getValue()
                    .getType().width - PerkButton.BIGGEST) / 2;
                int addy = (e.getValue()
                    .getType().height - PerkButton.BIGGEST) / 2;

                int subx = e.getValue()
                    .getType().width / 2;
                int suby = e.getValue()
                    .getType().height / 2;

                int x = (e.getKey().x * PerkButton.SPACING + 2) + addx - subx + SpellSchoolButton.XSIZE / 2;
                int y = (e.getKey().y * PerkButton.SPACING + 2) + addy - suby + SpellSchoolButton.YSIZE / 2;

                this.newButton(new PerkButton(e.getValue(), x, y));
            });

        Point point = school.calcData.buttonLoc;

        int x = point.x * PerkButton.SPACING + 2;
        int y = point.y * PerkButton.SPACING + 2;

        this.newButton(new SpellSchoolButton(school, x, y));
        this.newButton(new SelectTreeButton(SelectTreeButton.LeftOrRight.LEFT, x - 15, y + SpellSchoolButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));
        this.newButton(new SelectTreeButton(SelectTreeButton.LeftOrRight.RIGHT, x + SpellSchoolButton.XSIZE + 1, y + SpellSchoolButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));

    }

    private void newButton(AbstractButtonWidget b) {
        this.addButton(b);
        originalButtonLocMap.put(b, new Point2D(b.x, b.y));
    }

    int tick_count = 0;

    int scrollX = 0;
    int scrollY = 0;

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.scrollX += deltaX;
        this.scrollY += deltaY;
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        this.buttons.forEach(b -> {
            if (originalButtonLocMap.containsKey(b)) {
                b.x = (int) (this.originalButtonLocMap.get(b)
                    .getX() + scrollX);
                b.y = (int) (this.originalButtonLocMap.get(b)
                    .getY() + scrollY);
            }
        });

        renderBackgroundTexture(0);

        super.render(matrix, x, y, ticks);
        this.tick_count++;

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));
    }

    public void renderBackgroundTexture(int vOffset) {
        //copied from Scree

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.client.getTextureManager()
            .bindTexture(Screen.BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0D, (double) this.height, 0.0D)
            .texture(0.0F, (float) this.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) this.width, (double) this.height, 0.0D)
            .texture((float) this.width / 32.0F, (float) this.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) this.width, 0.0D, 0.0D)
            .texture((float) this.width / 32.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex(0.0D, 0.0D, 0.0D)
            .texture(0.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        tessellator.draw();
    }

}
