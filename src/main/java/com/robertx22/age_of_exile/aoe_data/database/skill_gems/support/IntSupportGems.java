package com.robertx22.age_of_exile.aoe_data.database.skill_gems.support;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;

import java.util.Arrays;

public class IntSupportGems implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemBuilder.of("heal_crit_rate", "Heal Critical Chance Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.heal, SupportGemTags.spell),
            new StatModifier(5, 25, Stats.HEAL_CRIT_CHANCE.get())
        );

        SkillGemBuilder.of("heal_crit_dmg", "Heal Critical Damage Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.heal, SupportGemTags.spell),
            new StatModifier(10, 40, Stats.HEAL_CRIT_DAMAGE.get())
        );

        SkillGemBuilder.of("heal_power", "Heal Power Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.heal),
            new StatModifier(5, 25, Stats.HEAL_STRENGTH.get())
        );

        SkillGemBuilder.of("spell_crit_rate", "Spell Critical Chance Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.damage, SupportGemTags.spell),
            new StatModifier(5, 25, Stats.SPELL_CRIT_CHANCE.get())
        );

        SkillGemBuilder.of("spell_crit_dmg", "Spell Critical Damage Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.damage, SupportGemTags.spell),
            new StatModifier(10, 40, Stats.SPELL_CRIT_DAMAGE.get())
        );

        SkillGemBuilder.of("fire_chance", "Chance to Burn Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(10, 20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN))
        );
        SkillGemBuilder.of("frostburn_chance", "Chance to Frostburn Support", new StatRequirement().setBaseInt(20)
                .setInt(0.2F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(10, 20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN))
        );

        SkillGemBuilder.of("cast_speed", "Faster Casting Support", new StatRequirement().setBaseInt(25)
                .setInt(0.4F), StatAttribute.INT, 1.2F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(5, 25, Stats.CAST_SPEED.get())
        );

        SkillGemBuilder.of("cooldown", "Less Cooldown Support", new StatRequirement().setBaseInt(25)
                .setInt(0.4F), StatAttribute.INT, 1.2F,
            Arrays.asList(),
            new StatModifier(10, 30, Stats.COOLDOWN_REDUCTION.get())
        );

        SkillGemBuilder.of("less_radius", "Concentrated Impact Support", new StatRequirement().setBaseInt(25)
                .setInt(0.5F), StatAttribute.INT, 1.1F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(-10, -20, Stats.INCREASED_AREA.get()),
            new StatModifier(15, 25, Stats.AREA_DAMAGE.get())
        );

        SkillGemBuilder.of("more_radius", "Expanded Area Support", new StatRequirement().setBaseInt(25)
                .setInt(0.5F), StatAttribute.INT, 1.1F,
            Arrays.asList(SupportGemTags.damage),
            new StatModifier(10, 20, Stats.INCREASED_AREA.get())
        );

    }
}
