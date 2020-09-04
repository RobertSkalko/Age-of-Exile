package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SkillTreeButton extends TexturedButtonWidget {
    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/spellschoolbutton.png");

    public static int XSIZE = 82;
    public static int YSIZE = 82;

    public SkillTreeButton(int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
        });
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

}