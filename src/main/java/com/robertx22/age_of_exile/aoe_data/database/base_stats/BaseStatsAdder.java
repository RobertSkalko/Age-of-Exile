package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseStatsAdder implements ExileRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(Mana.getInstance(), 10);

        c.scaled(new AttackDamage(Elements.Physical), 1);

        c.nonScaled(new AttackDamage(Elements.Physical), 3);

        c.scaled(Health.getInstance(), 10);
        c.scaled(Mana.getInstance(), 30);
        c.scaled(HealthRegen.getInstance(), 1);
        c.scaled(ManaRegen.getInstance(), 1);

        c.nonScaled(HealthRegen.getInstance(), 2);
        c.nonScaled(ManaRegen.getInstance(), 2);

        // why did i add this again? I think its a must
        c.nonScaled(Stats.CRIT_CHANCE.get(), 1);
        c.nonScaled(Stats.CRIT_DAMAGE.get(), 1);
        c.nonScaled(Stats.HEAL_CRIT_DAMAGE.get(), 1);
        c.nonScaled(Stats.HEAL_CRIT_CHANCE.get(), 1);

        return c;

    }

}
