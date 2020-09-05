package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class SelectTreeButton extends TexturedButtonWidget {

    public enum LeftOrRight {
        LEFT, RIGHT
    }

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/left_right.png");

    public static int XSIZE = 14;
    public static int YSIZE = 22;
    LeftOrRight way;
    SkillTreeScreen screen;

    public SelectTreeButton(SkillTreeScreen screen, LeftOrRight way, int x, int y) {
        super(x, y, XSIZE, YSIZE, getXoffset(way), 0, 1, ID, (action) -> {

            if (way == LeftOrRight.LEFT) {
                Optional<SpellSchool> opt = screen.schoolsInOrder.stream()
                    .filter(o -> o.order <= screen.school.order && !screen.school.identifier.equals(o.identifier))
                    .findFirst();

                if (opt.isPresent()) {
                    screen.school = opt.get();
                } else {
                    screen.school = screen.schoolsInOrder.get(0);
                }

            } else {
                Optional<SpellSchool> opt = screen.schoolsInOrder.stream()
                    .filter(o -> o.order >= screen.school.order && !screen.school.identifier.equals(o.identifier))
                    .findFirst();

                if (opt.isPresent()) {
                    screen.school = opt.get();
                } else {
                    screen.school = screen.schoolsInOrder.get(screen.schoolsInOrder.size() - 1);
                }
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