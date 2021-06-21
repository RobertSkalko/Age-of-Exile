package com.robertx22.age_of_exile.aoe_data.database.skill_gems.support;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.LeechInfo;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;

public class DexSupportGems implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("piercing_proj", "Piercing Projectiles Support", new StatRequirement().setBaseDex(20)
                .setDex(0.2F), StatAttribute.DEX, 1.5F,
            Arrays.asList(SupportGemTags.projectile),
            new StatModifier(1, 1, Stats.PIERCING_PROJECTILES.get())
        );

        SkillGemBuilder.of("faster_proj", "Faster Projectiles Support", new StatRequirement().setBaseDex(20)
                .setDex(0.2F), StatAttribute.DEX, 1.2F,
            Arrays.asList(SupportGemTags.projectile),
            new StatModifier(10, 20, Stats.PROJECTILE_SPEED.get()),
            new StatModifier(10, 20, Stats.PROJECTILE_DAMAGE.get())
        );

        SkillGemBuilder.of("poison_chance", "Chance to Poison Support", new StatRequirement().setBaseDex(20)
                .setDex(0.2F), StatAttribute.DEX, 1.25F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(10, 20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON))
        );
        SkillGemBuilder.of("mana_leech", "Mana Leech Support", new StatRequirement().setBaseDex(20)
                .setDex(0.2F), StatAttribute.DEX, 1.25F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(1, 2, Stats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.All, ResourceType.mana)))
        );

        SkillGemBuilder.of("atk_speed", "Faster Attacks Support", new StatRequirement().setBaseDex(25)
                .setDex(0.3F), StatAttribute.DEX, 1.25F,
            Arrays.asList(SupportGemTags.attack),
            new StatModifier(5, 25, Stats.ATTACK_SPEED.get())
        );

    }
}
