package com.robertx22.age_of_exile.config.base_player_stat;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
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
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashSet;
import java.util.Set;

public class BasePlayerStatContainer implements ISlashRegistryInit, IApplyableStats {

    public static BasePlayerStatContainer INSTANCE = new BasePlayerStatContainer();

    public static BasePlayerStatContainer defaultStats() {

        BasePlayerStatContainer c = new BasePlayerStatContainer();

        //base ones

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

    Set<OptScaleExactStat> base_stats = new HashSet<>();

    public void scaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, val, stat, ModType.FLAT).scale());
    }

    public void nonScaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, val, stat, ModType.FLAT));
    }

    @Override
    public void registerAll() {
        INSTANCE = this;
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        base_stats.forEach(x -> x.applyStats(data));

    }

}
