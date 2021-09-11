package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;

import java.util.List;

public class SkillGemBuilder {

    public static SkillGem of(String id, String locname, StatAttribute attri, float manaMulti, List<SupportGemTags> tags) {
        SkillGem gem = new SkillGem();

        gem.identifier = id;
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.type = SkillGemType.SUPPORT_GEM;
        gem.attribute = attri;
        gem.tags = tags;

        gem.addToSerializables();

        return gem;

    }

    public static SkillGem spell(Spell spell, String id, String locname, StatAttribute attri, float manaMulti) {
        SkillGem gem = new SkillGem();

        gem.weight = spell.weight;
        gem.identifier = id;
        gem.spell_id = spell.GUID();
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.type = SkillGemType.SKILL_GEM;
        gem.attribute = attri;

        gem.addToSerializables();

        return gem;

    }

}
