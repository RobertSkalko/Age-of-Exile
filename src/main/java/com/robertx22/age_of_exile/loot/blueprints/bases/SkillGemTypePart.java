package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;

public class SkillGemTypePart extends BlueprintPart<SkillGem, SkillGemBlueprint> {

    public SkillGemTypePart(SkillGemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected SkillGem generateIfNull() {

        if (blueprint.info != null) {
            if (blueprint.info.playerData == null || blueprint.info.level > 20) {
                return Database.SkillGems()
                    .random();
            }
        }

        try {
            return Database.SkillGems()
                .getFilterWrapped(x -> x.req.meetsReq(blueprint.info.level, blueprint.info.playerData))
                .random();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Database.SkillGems()
            .random();
    }
}
