package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SeasonRings implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "spring_blossoms",
                "Spring Blossoms",
                BaseGearKeys.RING)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(10, 10, SpecialStats.HEAL_CLEANSE, ModType.FLAT),
                new StatModifier(5, 10, HealthRegen.getInstance(), ModType.PERCENT),
                new StatModifier(10, 15, Stats.HEAL_STRENGTH.get(), ModType.FLAT),
                new StatModifier(15, 25, new ElementalResist(Elements.Earth), ModType.PERCENT)
            ))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
                "autumn_harvest",
                "Autumn Harvest",
                BaseGearKeys.RING)
            .setReplacesName()

            .stats(Arrays.asList(
                new StatModifier(10, 20, new ElementalResist(Elements.Earth), ModType.PERCENT),
                new StatModifier(10, 20, new ElementalResist(Elements.Fire), ModType.PERCENT),
                new StatModifier(10, 20, new ElementalResist(Elements.Water), ModType.PERCENT),
                new StatModifier(25, 25, SpecialStats.BETTER_FOOD_BUFFS, ModType.FLAT),
                new StatModifier(5, 10, Armor.getInstance(), ModType.PERCENT),
                new StatModifier(5, 10, DodgeRating.getInstance(), ModType.PERCENT),
                new StatModifier(2, 3, AllAttributes.getInstance(), ModType.FLAT)
            ))

            .gearSet(GearSetsAdder.SEASONS_SET)

            .devComment("")
            .build();

        UniqueGearBuilder.of(
                "winter_chill",
                "Winter Chill",
                BaseGearKeys.RING)
            .setReplacesName()
            .stats(Arrays.asList(new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Water), ModType.PERCENT),
                new StatModifier(10, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(5, 15, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT)
            ))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();

        UniqueGearBuilder.of(
                "summer_heat",
                "Summer Heat",
                BaseGearKeys.RING)
            .setReplacesName()

            .stats(Arrays.asList(new StatModifier(5, 10, Health.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, new ElementalResist(Elements.Fire), ModType.PERCENT),
                new StatModifier(10, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new StatModifier(5, 15, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(5, 15, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT)
            ))
            .gearSet(GearSetsAdder.SEASONS_SET)
            .devComment("")
            .build();
    }
}
