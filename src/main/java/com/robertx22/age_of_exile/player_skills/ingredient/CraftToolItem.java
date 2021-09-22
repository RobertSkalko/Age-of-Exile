package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class CraftToolItem extends AutoItem {

    PlayerSkillEnum skill;

    public CraftToolItem(PlayerSkillEnum skill) {

        super(new Properties().tab(CreativeTabs.Professions));
        this.skill = skill;
    }

    @Override
    public String locNameForLangFile() {
        return "todo";
    }

    @Override
    public String GUID() {
        return "craft_tools/" + skill.id;
    }
}
