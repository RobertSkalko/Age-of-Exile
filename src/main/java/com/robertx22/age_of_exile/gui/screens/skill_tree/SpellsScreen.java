package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.Identifier;

public class SpellsScreen extends SkillTreeScreen {

    public SpellsScreen() {
        super(SpellSchool.SchoolType.SPELLS);
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/skill_tree.png");
    }

    @Override
    public Words screenName() {
        return Words.SkillTrees;
    }
}
