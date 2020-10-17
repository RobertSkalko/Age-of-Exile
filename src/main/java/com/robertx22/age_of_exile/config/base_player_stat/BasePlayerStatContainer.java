package com.robertx22.age_of_exile.config.base_player_stat;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;

import java.util.HashMap;

public class BasePlayerStatContainer implements ISlashRegistryInit, IApplyableStats {

    public static BasePlayerStatContainer INSTANCE = new BasePlayerStatContainer();

    public static BasePlayerStatContainer defaultStats() {

        BasePlayerStatContainer c = new BasePlayerStatContainer();

        //base ones

        c.base(Health.getInstance(), 5);
        c.base(HealthRegen.getInstance(), 1);
        c.base(MagicShieldRegen.getInstance(), 1);
        c.base(CriticalHit.getInstance(), 1);
        c.base(CriticalDamage.getInstance(), 0);
        c.base(Mana.getInstance(), 25);
        c.base(ManaRegen.getInstance(), 1F);

        return c;

    }

    public HashMap<String, Double> BASE_PLAYER_STATS = new HashMap<>();

    public void base(Stat stat, double val) {
        BASE_PLAYER_STATS.put(stat.GUID(), val);
    }

    @Override
    public void registerAll() {
        INSTANCE = this;
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        this.BASE_PLAYER_STATS.entrySet()
            .forEach(x -> {
                data.getUnit()
                    .getStatInCalculation(x.getKey())
                    .addFlat(x.getValue()
                        .floatValue(), data.getLevel());
            });

    }

}
