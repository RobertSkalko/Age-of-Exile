package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ManaCost;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillGemBuilder {

    public static SkillGem of(String id, String locname, StatRequirement req, StatAttribute attri, float manaMulti, List<SkillGemTag> tags, StatModifier... stats) {
        SkillGem gem = new SkillGem();

        gem.identifier = id;
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.stats = Arrays.asList(stats);
        gem.type = SkillGemType.SUPPORT_GEM;
        gem.attribute = attri;
        gem.tags = tags;
        gem.req = req;

        if (manaMulti > 1) {
            int mana = (int) ((manaMulti - 1F) * 100F);
            gem.stats = new ArrayList<>(gem.stats);
            gem.stats.add(new StatModifier(mana, mana, ManaCost.getInstance()));
        }

        gem.addToSerializables();

        return gem;

    }

    public static SkillGem spell(Spell spell, String id, String locname, StatAttribute attri, float manaMulti) {
        SkillGem gem = new SkillGem();

        gem.identifier = id;
        gem.spell_id = spell.GUID();
        gem.locname = locname;
        gem.mana_multi = manaMulti;
        gem.stats = Arrays.asList();
        gem.type = SkillGemType.SKILL_GEM;
        gem.attribute = attri;
        gem.tags = spell.config.tags;

        StatRequirement req = new StatRequirement();
        if (attri == StatAttribute.STR) {
            req.setBaseStr(20);
            req.setStr(0.5F);
        } else if (attri == StatAttribute.INT) {
            req.setBaseInt(20);
            req.setInt(0.5F);
        } else if (attri == StatAttribute.DEX) {
            req.setBaseDex(20);
            req.setDex(0.5F);
        }

        gem.req = req;

        gem.addToSerializables();

        return gem;

    }

}
