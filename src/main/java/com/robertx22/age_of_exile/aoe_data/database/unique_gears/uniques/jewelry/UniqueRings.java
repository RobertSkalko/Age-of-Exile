package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueRings implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.WITCH_BREW_RING,
                "witch_brew",
                "Witch's Brew",
                "",
                BaseGearJewelry.MANA_RING.values())
            .baseStats(
                new StatModifier(15, 25, new ElementalResist(Elements.Nature), ModType.FLAT),
                new StatModifier(15, 25, new ElementalResist(Elements.Dark), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(25, 50, SpecialStats.BETTER_FOOD_BUFFS, ModType.FLAT),
                new StatModifier(10, 15, SpellDamage.getInstance(), ModType.FLAT),
                new StatModifier(5, 10, ManaRegen.getInstance(), ModType.PERCENT)

            ))
            .req(new StatRequirement()
                .setInt(0.5F)
                .setWis(0.5F))
            .devComment("Food buff mage ring")
            .build();

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.GHOSTLY_SHORES_RING,
                "ghostly_shores",
                "Ghostly Shores",
                BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    new StatModifier(10, 25, new ElementalResist(Elements.Water)),
                    new StatModifier(15, 45, new ElementalResist(Elements.Dark))
                )
            )
            .stats(Arrays.asList(
                new StatModifier(5, 15, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT),
                new StatModifier(6, 10, DodgeRating.getInstance(), ModType.PERCENT),
                new StatModifier(6, 15, Stats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT),
                new StatModifier(10, 15, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
                new StatModifier(5, 10, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT),
                new StatModifier(-3, -6, DatapackStats.VIT, ModType.FLAT),
                new StatModifier(15, 25, SpecialStats.BONUS_REGEN_IN_WATER, ModType.FLAT)
            ))
            .req(new StatRequirement().setWis(0.5F)
                .setDex(0.4F))
            .build();

    }
}