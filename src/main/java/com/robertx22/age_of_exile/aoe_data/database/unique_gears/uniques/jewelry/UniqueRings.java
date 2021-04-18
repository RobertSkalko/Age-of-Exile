package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.EntityType;

import java.util.Arrays;

public class UniqueRings implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.GOLD_RING,
            "gold_ring",
            "Gold Ring",
            "",
            BaseGearJewelry.MANA_RING.values())
            .setReplacesName()
            .baseStats(
                new StatModifier(-20, -20, Health.getInstance(), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 20, new ElementalResist(Elements.Light), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(1, 25, TreasureQuantity.getInstance(), ModType.FLAT),
                new StatModifier(1, 25, TreasureQuality.getInstance(), ModType.FLAT),
                new StatModifier(1, 5, Intelligence.INSTANCE, ModType.FLAT),
                new StatModifier(1, 5, Agility.INSTANCE, ModType.FLAT)

            ))
            .req(new StatRequirement()
                .setVit(0.5F)
                .setWis(0.5F))
            .devComment("item find ring")
            .build();

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
                new StatModifier(5, 10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)

            ))
            .mobFilter(EntityType.WITCH)
            .req(new StatRequirement()
                .setInt(0.5F)
                .setWis(0.5F))
            .devComment("Food buff mage ring")
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.LOOP_OF_INFINITY_RING,
            "loop_of_infinity",
            "Loop of Infinity",
            "Is it truly an end if everything just starts all over again? Maybe it really is just a loop.",
            BaseGearJewelry.MANA_RING.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT),
                new StatModifier(1, 4, RegeneratePercentStat.MANA, ModType.FLAT)
            ))
            .req(new StatRequirement().setWis(0.5F)
                .setInt(0.75F))
            .build();

    }
}