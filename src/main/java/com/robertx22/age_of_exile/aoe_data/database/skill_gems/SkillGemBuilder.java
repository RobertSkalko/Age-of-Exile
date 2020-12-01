package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;

import java.util.Arrays;

public class SkillGemBuilder {

    public static SkillGem of(String id, String locname, StatAttribute attri, SkillGemType type, float manaMulti, StatModifier... stats) {
        SkillGem gem = new SkillGem();

        gem.identifier = id;
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.stats = Arrays.asList(stats);
        gem.type = type;
        gem.attribute = attri;

        gem.addToSerializables();

        return gem;

    }
}
