package com.robertx22.age_of_exile.aoe_data.database.random_skill_gem_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.random_skill_gem_stats.RandomSkillGemStats;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;
import java.util.List;

public class RandomSkillGemStatsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of("fast_proj", Arrays.asList(SpellTag.projectile),
            new StatModifier(2, 10, Stats.PROJECTILE_SPEED.get())
        );
        of("proj_dmg", Arrays.asList(SpellTag.projectile),
            new StatModifier(2, 5, Stats.PROJECTILE_DAMAGE.get())
        );

    }

    public static void of(String id, int weight, List<SpellTag> tags, StatModifier... stats) {
        new RandomSkillGemStats(id, weight, Arrays.asList(stats), tags).addToSerializables();
    }

    public static void of(String id, List<SpellTag> tags, StatModifier... stats) {
        of(id, 1000, tags, stats);
    }

}
