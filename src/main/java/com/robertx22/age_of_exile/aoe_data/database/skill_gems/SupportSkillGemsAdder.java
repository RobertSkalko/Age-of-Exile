package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileAmountStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SupportSkillGemsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("minor_multi_proj", "Minor Multiple Projectiles Support", 2,
            new StatModifier(2, 2, ProjectileAmountStat.getInstance())
        );

    }
}
