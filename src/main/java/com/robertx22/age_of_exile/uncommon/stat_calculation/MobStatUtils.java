package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ElementalDodge;
import com.robertx22.age_of_exile.database.data.stats.types.defense.SpellDodge;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.TotalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MobStatUtils {

    public static void increaseMobStatsPerTier(LivingEntity en, UnitData mobdata, Unit unit) {

        for (InCalcStatData data : unit.getStats().statsInCalc
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

    public static List<StatContext> addAffixStats(LivingEntity en) {
        List<StatContext> list = new ArrayList<>();
        Load.Unit(en)
            .getAffixData()
            .getAffixes()
            .forEach(x -> list.addAll(x.getStatAndContext(en)));
        return list;

    }

    public static List<StatContext> worldMultiplierStats(LivingEntity en) {
        // todo does this work?

        List<StatContext> list = new ArrayList<>();

        List<ExactStatData> stats = new ArrayList<>();

        float val = (1F - Database.getDimensionConfig(en.world).mob_strength_multi) * 100F;

        stats.add(ExactStatData.noScaling(val, val, ModType.GLOBAL_INCREASE, Health.getInstance()
            .GUID()));
        stats.add(ExactStatData.noScaling(val, val, ModType.GLOBAL_INCREASE, TotalDamage.getInstance()
            .GUID()));

        return list;

    }

    public static void modifyMobStatsByConfig(LivingEntity entity, UnitData unitdata) {

        Unit unit = unitdata.getUnit();
        EntityConfig config = Database.getEntityConfig(entity, unitdata);

        // TODO TODO TODO  config.stats.stats.forEach(x -> x.applyStats(unitdata));

        for (InCalcStatData data : unit.getStats().statsInCalc
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

        MobRarity rar = Database.MobRarities()
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

        unit.getStatInCalculation(DodgeRating.getInstance())
            .addFlat(50, lvl);
        unit.getStatInCalculation(ElementalDodge.getInstance())
            .addFlat(50, lvl);
        unit.getStatInCalculation(SpellDodge.getInstance())
            .addFlat(9, lvl);
        unit.getStatInCalculation(Accuracy.getInstance())
            .addFlat(2, lvl);

        unit.getStatInCalculation(Armor.GUID)
            .addFlat(20 * rar.StatMultiplier(), lvl);
        unit.getStatInCalculation(CriticalHit.GUID)
            .addFlat(5 * rar.DamageMultiplier(), lvl);

        ElementalResist.MAP.getList()
            .forEach(x -> unit.getStatInCalculation(x)
                .addFlat(5 * rar.StatMultiplier(), lvl));

        unit.getStatInCalculation(SpellDamage.getInstance())
            .addFlat(-25, lvl); // less spell dmg, spells are already kinda strong

        float bonusEleDmg = 200F / ModConfig.get().Server.MAX_LEVEL * lvl;

        new ElementalDamageBonus(Elements.Water).generateAllPossibleStatVariations()
            .forEach(x -> unit.getStatInCalculation(x)
                .addFlat(bonusEleDmg, 1)); // the higher lvls go, the more important elemental resistances would be

    }

}
