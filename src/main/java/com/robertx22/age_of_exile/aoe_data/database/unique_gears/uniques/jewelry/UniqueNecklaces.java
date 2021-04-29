package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToGetEffectOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueNecklaces implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BLOOD_STONE_NECKLACE,
            "blood_stone",
            "Primordial Blood",
            BaseGearJewelry.HP_NECKLACE.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(10, 15, Health.getInstance()),
                    new StatModifier(15, 35, new ElementalResist(Elements.Dark)))
            )
            .stats(Arrays.asList(
                new StatModifier(25, 25, ChanceToGetEffectOnKill.BLOODLUST, ModType.FLAT),
                new StatModifier(5, 15, AttackSpeed.getInstance(), ModType.FLAT),
                new StatModifier(3, 5, Lifesteal.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, Health.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(2, 6, Vitality.INSTANCE, ModType.FLAT)
            ))
            .setReplacesName()
            .req(new StatRequirement()
                .setVit(0.8F)
                .setStr(0.5F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.GHAST_TEAR_NECKLACE,
            "ghast_necklace",
            "Ghast Tear",
            "",
            BaseGearJewelry.HP_NECKLACE.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalResist(Elements.Water)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Fire)))
            )
            .stats(Arrays.asList(
                new StatModifier(25, 25, DatapackStatAdder.MANA_PER_10_INT, ModType.FLAT),
                new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(3, 5, SpellDamage.getInstance(), ModType.FLAT),
                new StatModifier(1, 3, Vitality.INSTANCE, ModType.FLAT),
                new StatModifier(2, 6, Agility.INSTANCE, ModType.FLAT)

            ))
            .req(new StatRequirement()
                .setVit(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.SKULL_OF_SPIRITS_NECKLACE,
            "skull_of_spirits",
            "Skull of Spirits",
            "The mysterious skull contains knowledge of the contained spirits, but also their madness.",
            BaseGearJewelry.ALL_RES_NECKLACE.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(1, 2, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(10, 20, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(-5, -15, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

    }
}
