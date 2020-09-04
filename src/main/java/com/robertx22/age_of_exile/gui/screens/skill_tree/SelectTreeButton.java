package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class SelectTreeButton extends TexturedButtonWidget {

    public enum LeftOrRight {
        LEFT, RIGHT
    }

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/left_right.png");

    public static int XSIZE = 14;
    public static int YSIZE = 22;

    public SelectTreeButton(LeftOrRight way, int x, int y) {
        super(x, y, XSIZE, YSIZE, getXoffset(way), 0, 1, ID, (action) -> {
        });
    }

    static int getXoffset(LeftOrRight way) {

        if (way == LeftOrRight.LEFT) {
            return 15;
        } else {
            return 0;
        }

    }
}