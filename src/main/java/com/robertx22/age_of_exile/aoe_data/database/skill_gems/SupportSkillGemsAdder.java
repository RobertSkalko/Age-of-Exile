package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.types.offense.ProjectileDamage;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.PiercingProjectile;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileAmountStat;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileSpeedStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;

public class SupportSkillGemsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("minor_multi_proj", "Minor Multiple Projectiles Support", StatAttribute.DEX, 2,
            Arrays.asList(SkillGemTag.PROJECTILE),
            new StatModifier(2, 2, ProjectileAmountStat.getInstance())
        );
        SkillGemBuilder.of("major_multi_proj", "Major Multiple Projectiles Support", StatAttribute.DEX, 4,
            Arrays.asList(SkillGemTag.PROJECTILE),
            new StatModifier(4, 4, ProjectileAmountStat.getInstance())
        );
        SkillGemBuilder.of("piercing_proj", "Piercing Projectiles Support", StatAttribute.DEX, 1.5F,
            Arrays.asList(SkillGemTag.PROJECTILE),
            new StatModifier(1, 1, PiercingProjectile.getInstance())
        );

        SkillGemBuilder.of("faster_proj", "Faster Projectiles Support", StatAttribute.DEX, 1.2F,
            Arrays.asList(SkillGemTag.PROJECTILE),
            new StatModifier(10, 20, ProjectileSpeedStat.getInstance()),
            new StatModifier(10, 20, ProjectileDamage.getInstance())
        );

    }
}
