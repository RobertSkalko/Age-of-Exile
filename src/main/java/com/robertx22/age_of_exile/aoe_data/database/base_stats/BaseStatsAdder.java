package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellAccuracy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class BaseStatsAdder implements ISlashRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(Health.getInstance(), 20);
        c.nonScaled(Mana.getInstance(), 40);
        c.nonScaled(CriticalHit.getInstance(), 1);
        c.nonScaled(CriticalDamage.getInstance(), 0);

        c.scaled(SpellAccuracy.getInstance(), 10);
        c.scaled(Accuracy.getInstance(), 20);
        c.scaled(Health.getInstance(), 10);
        c.scaled(Mana.getInstance(), 10);
        c.scaled(ManaRegen.getInstance(), 1.5F);
        c.scaled(HealthRegen.getInstance(), 1);
        c.scaled(MagicShieldRegen.getInstance(), 1);
        c.scaled(new AttackDamage(Elements.Physical), 1);

        return c;

    }

}
