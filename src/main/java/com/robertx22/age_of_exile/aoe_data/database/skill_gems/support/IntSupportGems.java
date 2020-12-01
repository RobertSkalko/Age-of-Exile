package com.robertx22.age_of_exile.aoe_data.database.skill_gems.support;

import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ResourceLeech;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;

public class IntSupportGems implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("spell_crit_rate", "Spell Critical Chance Support", StatAttribute.INT, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(5, 25, SpellCriticalHit.getInstance())
        );

        SkillGemBuilder.of("spell_crit_dmg", "Spell Critical Damage Support", StatAttribute.INT, 1.2F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(10, 40, SpellCriticalDamage.getInstance())
        );

        SkillGemBuilder.of("fire_chance", "Chance to Burn Support", StatAttribute.INT, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(10, 20, ChanceToApplyEffect.POISON)
        );
        SkillGemBuilder.of("frostburn_chance", "Chance to Frostburn Support", StatAttribute.INT, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(10, 20, ChanceToApplyEffect.POISON)
        );

        SkillGemBuilder.of("shield_leech", "Magic Shield Leech Support", StatAttribute.INT, 1.25F,
            Arrays.asList(SkillGemTag.DAMAGE),
            new StatModifier(1, 2, new ResourceLeech(new ResourceLeech.Info(Elements.All, ResourceType.MAGIC_SHIELD, AttackType.ALL)))
        );

    }
}
