package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillGemBuilder {

    public static SkillGem of(String id, String locname, StatRequirement req, StatAttribute attri, float manaMulti, List<SupportGemTags> tags, StatModifier... stats) {
        SkillGem gem = new SkillGem();

        gem.identifier = id;
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.stats = Arrays.asList(stats);
        gem.type = SkillGemType.SUPPORT_GEM;
        gem.attribute = attri;
        gem.tags = tags;

        if (manaMulti > 1) {
            int mana = (int) ((manaMulti - 1F) * 100F);
            gem.stats = new ArrayList<>(gem.stats);
            gem.stats.add(new StatModifier(mana, mana, Stats.MANA_COST.get()));
        }

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
        gem.stats = Arrays.asList();
        gem.type = SkillGemType.SKILL_GEM;
        gem.attribute = attri;

        gem.addToSerializables();

        return gem;

    }

}
