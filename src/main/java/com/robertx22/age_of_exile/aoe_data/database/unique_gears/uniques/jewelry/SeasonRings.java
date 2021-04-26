package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class SeasonRings implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.SPRING,
            "spring_blossoms",
            "Spring Blossoms",
            BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                new StatModifier(15, 25, new ElementalResist(Elements.Nature), ModType.FLAT),
                new StatModifier(15, 25, new ElementalResist(Elements.Light), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(10, 10, SpecialStats.HEAL_CLEANSE, ModType.FLAT),
                new StatModifier(5, 10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(5, 10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(10, 15, HealPower.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setWis(0.7F)
                .setVit(0.5F))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.AUTUMN,
            "autumn_harvest",
            "Autumn Harvest",
            BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                new StatModifier(10, 20, new ElementalResist(Elements.Nature), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Water), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(25, 25, SpecialStats.BETTER_FOOD_BUFFS, ModType.FLAT),
                new StatModifier(5, 10, Armor.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(5, 10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(2, 3, AllAttributes.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setVit(0.6F)
                .setWis(0.3F))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.WINTER,
            "winter_chill",
            "Winter Chill",
            BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Water), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(10, 10, ChanceToApplyEffect.FROSTBURN, ModType.FLAT),
                new StatModifier(10, 20, new ElementalDamageBonus(Elements.Water), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(5, 15, SpellCriticalHit.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setInt(0.6F)
                .setWis(0.3F))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.SUMMER,
            "summer_heat",
            "Summer Heat",
            BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Fire), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(10, 10, ChanceToApplyEffect.BURN, ModType.FLAT),
                new StatModifier(10, 20, new ElementalDamageBonus(Elements.Fire), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(5, 15, SpellCriticalDamage.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setInt(0.6F)
                .setWis(0.3F))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();
    }
}
