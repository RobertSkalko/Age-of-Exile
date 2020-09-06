package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class WholeSpellHotbarButton extends TexturedButtonWidget {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/hotbar.png");

    public static int XSIZE = 193;
    public static int YSIZE = 34;
    SkillTreeScreen screen;

    public WholeSpellHotbarButton(SkillTreeScreen screen, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {

        });
        this.screen = screen;
    }

}