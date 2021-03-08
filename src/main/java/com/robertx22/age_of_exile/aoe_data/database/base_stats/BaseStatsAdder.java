package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellAccuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class BaseStatsAdder implements ISlashRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        //c.nonScaled(Health.getInstance(), 10);
        //c.nonScaled(Mana.getInstance(), 10);

        c.nonScaled(CriticalHit.getInstance(), 1);
        c.nonScaled(CriticalDamage.getInstance(), 0);
        c.nonScaled(SpellCriticalHit.getInstance(), 1);
        c.nonScaled(SpellCriticalDamage.getInstance(), 0);

        c.scaled(SpellAccuracy.getInstance(), 10);
        c.scaled(Accuracy.getInstance(), 20);
        //c.scaled(Health.getInstance(), 5);
        //c.scaled(Mana.getInstance(), 5);
        //c.scaled(ManaRegen.getInstance(), 1F);
        //c.scaled(HealthRegen.getInstance(), 1);
        //c.scaled(new AttackDamage(Elements.Physical), 1);

        return c;

    }

}
