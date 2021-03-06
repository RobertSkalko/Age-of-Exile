package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class ClothUniques implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.INNFER_CONFLUX_ROBE,
            "inner_conflux",
            "Inner Conflux",
            "Merge the Rivers of Mana and Magic, attain infinity, or die.",
            BaseClothArmors.CHESTS.get(LevelRanges.ENDGAME))
            .stats(Arrays.asList(
                new StatModifier(5, 15, Health.getInstance(), ModType.FLAT),
                new StatModifier(10, 30, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(1, 2, RegeneratePercentStat.MANA, ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.JESTER_HAT,
            "jester_hat",
            "Jester's Intuition",
            "One, two, three, fail. Whoops! One, two..",
            BaseClothArmors.HELMETS.get(LevelRanges.HIGH))
            .stats(Arrays.asList(
                new StatModifier(-15, 15, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(-15, 15, CriticalDamage.getInstance(), ModType.FLAT),
                new StatModifier(-15, 15, TreasureQuality.getInstance(), ModType.FLAT),
                new StatModifier(-15, 15, TreasureQuantity.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.FROST_CROWN,
            "frost_crown",
            "Crown of Ice",
            "Everyone needs a proper burial, and I will make theirs dazzling.",
            BaseClothArmors.HELMETS.get(LevelRanges.MIDDLE))
            .stats(Arrays.asList(
                new StatModifier(10, 20, new ElementalSpellDamage(Elements.Water), ModType.FLAT),
                new StatModifier(25, 50, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(10, 20, new ElementalPenetration(Elements.Water), ModType.FLAT),
                new StatModifier(5, 15, ChanceToApplyEffect.FROSTBURN, ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.CROWN_OF_ELEMENTS,
            "crown_of_elements",
            "Crown of Elements",
            "Have you ever felt true power?",
            BaseClothArmors.HELMETS.get(LevelRanges.ENDGAME))
            .stats(Arrays.asList(
                new StatModifier(10, 20, new ElementalSpellDamage(Elements.Water), ModType.FLAT),
                new StatModifier(10, 20, new ElementalSpellDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 20, new ElementalSpellDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(10, 20, new ElementalSpellDamage(Elements.Nature), ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

    }
}
