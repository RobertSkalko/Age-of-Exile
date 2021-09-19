package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class TalentTreeButton extends ImageButton implements IMarkOnTop {
    static ResourceLocation ID = new ResourceLocation(Ref.MODID, "textures/gui/skill_tree/spellschoolbutton.png");

    public static int XSIZE = 32;
    public static int YSIZE = 32;

    TalentTree school;

    SkillTreeScreen screen;

    public TalentTreeButton(SkillTreeScreen screen, TalentTree school, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
        });
        this.school = school;
        this.screen = screen;
    }

    @Override
    public void onPress() {
        screen.school = school;
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

        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager()
            .bind(new ResourceLocation(school.icon));
        RenderSystem.enableDepthTest();
        blit(matrices, this.x, this.y, 0, 0, XSIZE, XSIZE, XSIZE, XSIZE);
    }

}