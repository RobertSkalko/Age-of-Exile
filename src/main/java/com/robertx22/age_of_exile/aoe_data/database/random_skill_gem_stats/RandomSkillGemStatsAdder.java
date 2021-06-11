package com.robertx22.age_of_exile.aoe_data.database.random_skill_gem_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.random_skill_gem_stats.RandomSkillGemStats;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;
import java.util.List;

public class RandomSkillGemStatsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of("fast_proj", Arrays.asList(SupportGemTags.projectile),
            new StatModifier(2, 10, Stats.PROJECTILE_SPEED.get())
        );
        of("proj_dmg", Arrays.asList(SupportGemTags.projectile),
            new StatModifier(2, 5, Stats.PROJECTILE_DAMAGE.get())
        );

        Elements.getAllSingleElementals()
            .forEach(x -> {
                of(x.guidName + "_dmg", Arrays.asList(SupportGemTags.damage),
                    new StatModifier(2, 6, Stats.ELEMENTAL_DAMAGE.get(x))
                );
            });

        of("crit_chance", Arrays.asList(SupportGemTags.attack),
            new StatModifier(2, 4, Stats.CRIT_CHANCE.get())
        );
        of("crit_dmg", Arrays.asList(SupportGemTags.attack),
            new StatModifier(2, 6, Stats.CRIT_DAMAGE.get())
        );

        of("heal_crit_chance", Arrays.asList(SupportGemTags.heal),
            new StatModifier(2, 4, Stats.HEAL_CRIT_CHANCE.get())
        );
        of("heal_crit_dmg", Arrays.asList(SupportGemTags.heal),
            new StatModifier(2, 6, Stats.HEAL_CRIT_DAMAGE.get())
        );

        of("spell_crit_chance", Arrays.asList(SupportGemTags.spell),
            new StatModifier(2, 4, Stats.SPELL_CRIT_CHANCE.get())
        );
        of("spell_crit_dmg", Arrays.asList(SupportGemTags.spell),
            new StatModifier(2, 6, Stats.SPELL_CRIT_DAMAGE.get())
        );

        of("cdr", Arrays.asList(SupportGemTags.values()),
            new StatModifier(2, 4, Stats.COOLDOWN_REDUCTION.get())
        );

    }

    public static void of(String id, int weight, List<SupportGemTags> tags, StatModifier... stats) {
        new RandomSkillGemStats(id, weight, Arrays.asList(stats), tags).addToSerializables();
    }

    public static void of(String id, List<SupportGemTags> tags, StatModifier... stats) {
        of(id, 1000, tags, stats);
    }

    public static void of(String id, SupportGemTags tags, StatModifier... stats) {
        of(id, 1000, Arrays.asList(tags), stats);
    }

}
