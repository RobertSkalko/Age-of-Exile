package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mobs.mob_stats.SpecialMobStats;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public class MobStatUtils {

    static int spelldmg = 12;

    public static void increaseMobStatsPerTier(UnitData mobdata, Unit unit) {

        for (StatData data : unit.getStats()
            .values()
            .stream()
            .filter(x -> {
                return x.GetStat()
                    .IsPercent() == false;
            })
            .collect(Collectors.toList())) {

            data.multiplyFlat(mobdata.getMapTier().mob_stat_multi);
        }

    }

    public static void addAffixStats(UnitData data) {

        data.getUnit()
            .getAffixes()
            .forEach(x -> x.applyStats(data));

    }

    public static void worldMultiplierStats(World world, Unit unit) {
        for (StatData stat : unit.getStats()
            .values()) {
            stat.multiplyFlat(SlashRegistry.getDimensionConfig(world).mob_strength_multi);
        }

    }

    public static void modifyMobStatsByConfig(LivingEntity entity, UnitData unitdata) {

        Unit unit = unitdata.getUnit();
        EntityConfig config = SlashRegistry.getEntityConfig(entity, unitdata);

        for (StatData data : unit.getStats()
            .values()) {
            Stat stat = data.GetStat();
            if (stat instanceof WeaponDamage || stat instanceof ElementalSpellDamage || stat instanceof CriticalDamage || stat instanceof CriticalHit) {
                data.multiplyFlat(config.dmg_multi);
            } else if (data.getId()
                .equals(Health.GUID)) {
                data.multiplyFlat(config.hp_multi);
            } else {
                data.multiplyFlat(config.stat_multi);
            }
        }

    }

    public static void AddMobcStats(UnitData unitdata, LivingEntity en) {

        SpecialMobStats.getStatsFor(en).stats.forEach(x -> x.ToExactStat(100, unitdata.getLevel())
            .applyStats(unitdata));

        try {
            unitdata.getArea()
                .getAreaModifier()
                .stats.forEach(x -> x.ToExactStat(100, unitdata.getLevel())
                .applyStats(unitdata));
        } catch (Exception e) {
            e.printStackTrace();
        }

        MobRarity rar = Rarities.Mobs.get(unitdata.getRarity());
        Unit unit = unitdata.getUnit();

        int lvl = unitdata.getLevel();

        float hpToAdd = EntityUtils.getVanillaMaxHealth(en) * rar.ExtraHealthMulti();

        if (hpToAdd < 0) {
            hpToAdd = 0;
        }

        unit.getCreateStat(Health.getInstance())
            .addFlat(hpToAdd, lvl);

        unit.getCreateStat(Armor.GUID)
            .addFlat(Armor.getInstance()
                .valueNeededToReachMaximumPercentAtLevelOne() / 4 * rar.StatMultiplier(), lvl);
        unit.getCreateStat(CriticalHit.GUID)
            .addFlat(5 * rar.DamageMultiplier(), lvl);
        unit.getCreateStat(CriticalDamage.GUID)
            .addFlat(5 * rar.DamageMultiplier(), lvl);

        ElementalResist.MAP.getList()
            .forEach(x -> unit.getCreateStat(x)
                .addFlat(5 * rar.StatMultiplier(), lvl));

        ElementalSpellDamage.MAP.getList()
            .forEach(x -> unit.getCreateStat(x)
                .addFlat(spelldmg * rar.DamageMultiplier(), lvl));

        ElementalPenetration.MAP.getList()
            .forEach(x -> unit.getCreateStat(x)
                .addFlat(4 * rar.DamageMultiplier(), lvl));

    }

}
