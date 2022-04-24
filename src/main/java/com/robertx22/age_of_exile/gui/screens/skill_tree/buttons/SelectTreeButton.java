package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class SelectTreeButton extends ImageButton implements IMarkOnTop {

    public enum LeftOrRight {
        LEFT, RIGHT
    }

    static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/left_right.png");

    public static int XSIZE = 14;
    public static int YSIZE = 22;
    LeftOrRight way;
    SkillTreeScreen screen;

    public SelectTreeButton(SkillTreeScreen screen, LeftOrRight way, int x, int y) {
        super(x, y, XSIZE, YSIZE, getXoffset(way), 0, 1, ID, (action) -> {
            int place = screen.orderedTrees.indexOf(screen.tree);

            if (way == LeftOrRight.LEFT) {
                screen.tree = screen.getSchoolByIndexAllowsOutOfBounds(place - 1);
            } else {
                screen.tree = screen.getSchoolByIndexAllowsOutOfBounds(place + 1);
            }
            screen.refreshButtons();
        });

        this.way = way;
        this.screen = screen;
    }

    static int getXoffset(LeftOrRight way) {

        if (way == LeftOrRight.LEFT) {
            return 15;
        } else {
            return 0;
        }

    }
}