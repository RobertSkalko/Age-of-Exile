package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public class MobStatUtils {

    public static void increaseMobStatsPerTier(UnitData mobdata, Unit unit) {

        for (InCalcStatData data : unit.getStatsContainer().statsInCalc
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

        data.getAffixData()
            .getAffixes()
            .forEach(x -> x.applyStats(data));

    }

    public static void worldMultiplierStats(World world, Unit unit) {
        for (InCalcStatData stat : unit.getStatsContainer().statsInCalc
            .values()) {
            stat.multiplyFlat(SlashRegistry.getDimensionConfig(world).mob_strength_multi);
        }

    }

    public static void modifyMobStatsByConfig(LivingEntity entity, UnitData unitdata) {

        Unit unit = unitdata.getUnit();
        EntityConfig config = SlashRegistry.getEntityConfig(entity, unitdata);

        config.stats.stats.forEach(x -> x.applyStats(unitdata));

        for (InCalcStatData data : unit.getStatsContainer().statsInCalc
            .values()) {
            Stat stat = data.GetStat();
            if (stat instanceof AttackDamage || stat instanceof ElementalSpellDamage || stat instanceof CriticalDamage || stat instanceof CriticalHit) {
                data.multiplyFlat(config.dmg_multi);
            } else if (data.id
                .equals(Health.GUID)) {
                data.multiplyFlat(config.hp_multi);
            } else {
                data.multiplyFlat(config.stat_multi);
            }
        }

    }

    public static void AddMobcStats(UnitData unitdata, LivingEntity en) {

        try {
            for (StatModifier x : unitdata.getAreaMod()
                .stats) {
                x.ToExactStat(100, unitdata.getLevel())
                    .applyStats(unitdata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MobRarity rar = SlashRegistry.MobRarities()
            .get(unitdata.getRarity());
        Unit unit = unitdata.getUnit();

        int lvl = unitdata.getLevel();

        float hpToAdd = EntityUtils.getVanillaMaxHealth(en) * rar.ExtraHealthMulti();

        hpToAdd += (ModConfig.get().Server.EXTRA_MOB_STATS_PER_LEVEL * lvl) * hpToAdd;

        if (hpToAdd < 0) {
            hpToAdd = 0;
        }

        unit.getStatInCalculation(Health.getInstance())
            .addFlat(hpToAdd, lvl);

        unit.getStatInCalculation(Armor.GUID)
            .addFlat((Armor.getInstance()
                .valueNeededToReachMaximumPercentAtLevelOne() / 4) * rar.StatMultiplier(), lvl);
        unit.getStatInCalculation(CriticalHit.GUID)
            .addFlat(5 * rar.DamageMultiplier(), lvl);
        unit.getStatInCalculation(CriticalDamage.GUID)
            .addFlat(2 * rar.DamageMultiplier(), lvl);

        ElementalResist.MAP.getList()
            .forEach(x -> unit.getStatInCalculation(x)
                .addFlat(5 * rar.StatMultiplier(), lvl));

        unit.getStatInCalculation(SpellDamage.getInstance())
            .addFlat(-25, lvl); // less spell dmg, spells are already kinda strong

        float bonusEleDmg = 300F / ModConfig.get().Server.MAX_LEVEL * lvl;

        new ElementalDamageBonus(Elements.Water).generateAllPossibleStatVariations()
            .forEach(x -> unit.getStatInCalculation(x)
                .addFlat(bonusEleDmg, 1)); // the higher lvls go, the more important elemental resistances would be

    }

}
