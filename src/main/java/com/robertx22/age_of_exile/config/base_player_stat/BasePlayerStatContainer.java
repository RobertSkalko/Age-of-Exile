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

        c.nonScaled(Health.getInstance(), 10);
        c.nonScaled(HealthRegen.getInstance(), 2);
        c.nonScaled(MagicShieldRegen.getInstance(), 1);
        c.nonScaled(CriticalHit.getInstance(), 1);
        c.nonScaled(CriticalDamage.getInstance(), 0);

        c.scaled(Mana.getInstance(), 25);
        c.scaled(ManaRegen.getInstance(), 1.5F);

        return c;

    }

    public HashMap<String, Double> NON_SCALED = new HashMap<>();
    public HashMap<String, Double> SCALED_TO_LVL = new HashMap<>();

    public void scaled(Stat stat, double val) {
        SCALED_TO_LVL.put(stat.GUID(), val);
    }

    public void nonScaled(Stat stat, double val) {
        NON_SCALED.put(stat.GUID(), val);
    }

    @Override
    public void registerAll() {
        INSTANCE = this;
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {

        this.SCALED_TO_LVL.entrySet()
            .forEach(x -> {
                data.getUnit()
                    .getStatInCalculation(x.getKey())
                    .addFlat(x.getValue()
                        .floatValue(), data.getLevel());
            });
        this.NON_SCALED.entrySet()
            .forEach(x -> {
                data.getUnit()
                    .getStatInCalculation(x.getKey())
                    .addAlreadyScaledFlat(x.getValue()
                        .floatValue(), x.getValue()
                        .floatValue());
            });

    }

}
