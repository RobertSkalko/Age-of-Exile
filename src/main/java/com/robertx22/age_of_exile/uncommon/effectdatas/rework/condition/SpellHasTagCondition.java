package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class SpellHasTagCondition extends StatCondition {

    SkillGemTag tag;

    public SpellHasTagCondition(SkillGemTag tag) {
        super("spell_has_tag_" + tag, "spell_has_tag");
        this.tag = tag;
    }

    SpellHasTagCondition() {
        super("", "spell_has_tag");
    }

    @Override
    public boolean can(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        if (event.isSpell()) {
            return event.getSpell()
                .is(tag);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return SpellHasTagCondition.class;
    }

}
