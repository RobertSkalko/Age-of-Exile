package com.robertx22.age_of_exile.aoe_data.database.skill_gems.support;

import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ResourceLeech;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;

public class StrSupportGems implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("bleed_chance", "Chance to Bleed Support", StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(10, 20, ChanceToApplyEffect.BLEED)
        );

        SkillGemBuilder.of("health_leech", "Health Leech Support", StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(1, 2, new ResourceLeech(new ResourceLeech.Info(Elements.All, ResourceType.HEALTH, AttackType.ALL)))
        );

        SkillGemBuilder.of("crit_rate", "Critical Chance Support", StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(5, 25, CriticalHit.getInstance())
        );

        SkillGemBuilder.of("crit_dmg", "Critical Damage Support", StatAttribute.STR, 1.2F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(10, 40, CriticalDamage.getInstance())
        );

    }
}
