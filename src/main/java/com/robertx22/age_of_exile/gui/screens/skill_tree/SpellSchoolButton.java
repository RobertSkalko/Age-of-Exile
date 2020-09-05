package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpellSchoolButton extends TexturedButtonWidget {
    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/spellschoolbutton.png");

    public static int XSIZE = 32;
    public static int YSIZE = 32;

    String id;

    SpellSchool getSchool() {
        return SlashRegistry.SpellSchools()
            .get(id);
    }

    SkillTreeScreen screen;

    public SpellSchoolButton(SkillTreeScreen screen, SpellSchool school, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
        });
        this.id = school.identifier;
        this.screen = screen;
    }

    @Override
    public void onPress() {
        screen.school = getSchool();
        screen.refreshButtons();
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, XSIZE, YSIZE, x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        if (this.isInside(mouseX, mouseY)) {
            //GuiUtils.renderTooltip(matrices, this.tooltip, mouseX, mouseY);
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // super.renderButton(matrices, mouseX, mouseY, delta);

        MinecraftClient mc = MinecraftClient.getInstance();
        mc.getTextureManager()
            .bindTexture(new Identifier(getSchool().icon));
        RenderSystem.enableDepthTest();
        drawTexture(matrices, this.x, this.y, 0, 0, XSIZE, XSIZE, XSIZE, XSIZE);
    }

}