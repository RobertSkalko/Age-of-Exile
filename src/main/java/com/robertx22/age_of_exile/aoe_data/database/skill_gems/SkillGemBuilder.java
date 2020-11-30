package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellGem;

import java.util.Arrays;

public class SkillGemBuilder {

    public static SpellGem of(String id, String locname, float manaMulti, StatModifier... stats) {
        SpellGem gem = new SpellGem();

        gem.identifier = id;
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.stats = Arrays.asList(stats);

        gem.addToSerializables();

        return gem;

    }
}
