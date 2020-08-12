package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;

public class SpellPart extends BlueprintPart<BaseSpell> {

    public SpellPart(SkillGemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected BaseSpell generateIfNull() {
        return SlashRegistry.Spells()
            .random();
    }
}
