package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
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
                new StatModifier(5, 15, MagicShield.getInstance(), ModType.FLAT),
                new StatModifier(10, 30, MagicShield.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(1, 2, RegeneratePercentStat.MANA, ModType.FLAT),
                new StatModifier(1, 2, RegeneratePercentStat.MAGIC_SHIELD, ModType.FLAT),
                new StatModifier(-8, -3, Health.getInstance(), ModType.FLAT),
                new StatModifier(0.4F, 0.6F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
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
                new StatModifier(-15, 15, MagicFind.getInstance(), ModType.FLAT),
                new StatModifier(-15, 15, IncreasedItemQuantity.getInstance(), ModType.FLAT),
                new StatModifier(0.2F, 0.6F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();
    }
}
