package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueJewelry implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.SNAKE_EYE_NECKLACE,
            "snake_eye",
            "Snake Eye",
            "Eternally dead, and yet not blind.",
            BaseGearJewelry.HP_NECKLACE.get(LevelRanges.START_TO_LOW))
            .stats(Arrays.asList(
                new StatModifier(3, 8, ChanceToApplyEffect.POISON, ModType.FLAT),
                new StatModifier(3, 5, Lifesteal.getInstance(), ModType.FLAT),
                new StatModifier(0.5F, 0.35F, new FlatIncreasedReq(Dexterity.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.ANGEL_PROT_NECKLACE,
            "angel_prot_necklace",
            "Angelic Protection",
            "If your guardian is careful enough, you just might think they don't exist.",
            BaseGearJewelry.ALL_RES_NECKLACE.get(LevelRanges.START_TO_LOW))
            .stats(Arrays.asList(
                new StatModifier(50, 100, ManaBurnResistance.getInstance(), ModType.FLAT),
                new StatModifier(1, 1, ImmuneToEffectStat.POISON, ModType.FLAT),
                new StatModifier(3, 5, PlusResourceOnKill.MANA, ModType.FLAT),
                new StatModifier(0.4F, 0.55F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BIRTH_MIRACLE_NECKLACE,
            "birth_miracle",
            "Birthing Miracle",
            "Life can sometimes keep on giving.",
            BaseGearJewelry.HP_NECKLACE.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(3, 8, Health.getInstance(), ModType.FLAT),
                new StatModifier(0.1F, 0.2F, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(2, 5, PlusResourceOnKill.HEALTH, ModType.FLAT),
                new StatModifier(0.3F, 0.45F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.SKULL_OF_SPIRITS_NECKLACE,
            "skull_of_spirits",
            "Skull of Spirits",
            "The mysterious skull contains knowledge of the contained spirits, but also their madness.",
            BaseGearJewelry.ALL_RES_NECKLACE.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(0.15F, 0.4F, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(10, 20, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(-5, -15, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.GREED_PERSIST_RING,
            "greed_persist",
            "Greed's Persistence",
            "When desire for perfection overtakes your sanity, you too will be blessed.",
            BaseGearJewelry.FIRE_RES_RING.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(-30, 25, IncreasedItemQuantity.getInstance(), ModType.FLAT),
                new StatModifier(-30, 25, MagicFind.getInstance(), ModType.FLAT),
                new StatModifier(5, 10, new ElementalResist(Elements.Elemental), ModType.FLAT),
                new StatModifier(-10, -5, Health.getInstance(), ModType.FLAT)

            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.LOOP_OF_INFINITY_RING,
            "loop_of_infinity",
            "Loop of Infinity",
            "Is it truly an end if everything just starts all over again? Maybe it really is just a loop.",
            BaseGearJewelry.RING_MANA_REG.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT),
                new StatModifier(1, 4, RegeneratePercentStat.MANA, ModType.FLAT),
                new StatModifier(0.2F, 0.35F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();

    }

}

