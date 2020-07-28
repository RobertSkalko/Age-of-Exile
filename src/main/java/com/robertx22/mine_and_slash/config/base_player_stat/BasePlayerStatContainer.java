package com.robertx22.mine_and_slash.config.base_player_stat;

import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.*;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IApplyableStats;

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
        c.base(Mana.getInstance(), 40);
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
                    .getCreateStat(x.getKey())
                    .addFlat(x.getValue()
                        .floatValue(), data.getLevel());
            });

    }

}
