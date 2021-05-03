package com.robertx22.age_of_exile.aoe_data.database.skill_gems.support;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ManaCost;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;

public class StrSupportGems implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("bleed_chance", "Chance to Bleed Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(10, 20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED))
        );

        SkillGemBuilder.of("health_leech", "Health Leech Support", new StatRequirement().setBaseStr(25)
                .setStr(0.4F), StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(2, 3, Stats.LIFESTEAL.get())
        );

        SkillGemBuilder.of("crit_rate", "Critical Chance Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.25F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(5, 25, Stats.CRIT_CHANCE.get())
        );

        SkillGemBuilder.of("crit_dmg", "Critical Damage Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.2F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(10, 40, Stats.CRIT_DAMAGE.get())
        );

        SkillGemBuilder.of("water_dmg", "Cold Damage Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.2F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(5, 30, new ElementalDamageBonus(Elements.Water))
        );

        SkillGemBuilder.of("fire_dmg", "Fire Damage Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.2F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(5, 30, new ElementalDamageBonus(Elements.Fire))
        );

        SkillGemBuilder.of("nature_dmg", "Poison Damage Support", new StatRequirement().setBaseStr(20)
                .setStr(0.2F), StatAttribute.STR, 1.2F,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(5, 30, new ElementalDamageBonus(Elements.Nature))
        );

        SkillGemBuilder.of("less_mana_cost", "Mana Cost Reduction Support", new StatRequirement().setBaseStr(20)
                .setStr(0.3F), StatAttribute.STR, 1,
            Arrays.asList(SkillGemTag.damage),
            new StatModifier(-10, -20, ManaCost.getInstance())
        );
    }
}
