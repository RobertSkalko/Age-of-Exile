package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;

public class SkillGemRarityPart extends BlueprintPart<SkillGemRarity, SkillGemBlueprint> {

    public SkillGemRarityPart(SkillGemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected SkillGemRarity generateIfNull() {
        return ExileDB.SkillGemRarities()
            .random();
    }
}
