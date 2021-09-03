package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SeasonRings implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.GEAR_ITEMS.RINGS.get(VanillaMaterial.DIAMOND),
                "spring_blossoms",
                "Spring Blossoms",
                BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(10, 10, SpecialStats.HEAL_CLEANSE, ModType.FLAT),
                new StatModifier(5, 10, HealthRegen.getInstance(), ModType.PERCENT),
                new StatModifier(10, 15, Stats.HEAL_STRENGTH.get(), ModType.FLAT),
                new StatModifier(15, 25, new ElementalResist(Elements.Earth), ModType.FLAT)
            ))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
                ModRegistry.GEAR_ITEMS.RINGS.get(VanillaMaterial.DIAMOND),
                "autumn_harvest",
                "Autumn Harvest",
                BaseGearJewelry.MANA_RING.values())
            .setReplacesName()

            .stats(Arrays.asList(
                new StatModifier(10, 20, new ElementalResist(Elements.Earth), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(25, 25, SpecialStats.BETTER_FOOD_BUFFS, ModType.FLAT),
                new StatModifier(5, 10, Armor.getInstance(), ModType.PERCENT),
                new StatModifier(5, 10, DodgeRating.getInstance(), ModType.PERCENT),
                new StatModifier(2, 3, AllAttributes.getInstance(), ModType.FLAT)
            ))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
                ModRegistry.GEAR_ITEMS.RINGS.get(VanillaMaterial.DIAMOND),
                "winter_chill",
                "Winter Chill",
                BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .stats(Arrays.asList(new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(10, 10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN), ModType.FLAT),
                new StatModifier(10, 20, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(5, 15, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT)
            ))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();

        UniqueGearBuilder.of(
                ModRegistry.GEAR_ITEMS.RINGS.get(VanillaMaterial.DIAMOND),
                "summer_heat",
                "Summer Heat",
                BaseGearJewelry.MANA_RING.values())
            .setReplacesName()

            .stats(Arrays.asList(new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN), ModType.FLAT),
                new StatModifier(10, 20, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(5, 15, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT)
            ))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();
    }
}
