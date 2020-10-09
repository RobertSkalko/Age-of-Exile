package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.EffectImmunity;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DamageUnderPotion;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicSteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaBurnResistance;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueNecklaces implements ISlashRegistryInit {
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
                new StatModifier(4, 6, Lifesteal.getInstance(), ModType.FLAT),
                new StatModifier(0.1F, 0.2F, new FlatIncreasedReq(Dexterity.INSTANCE), ModType.FLAT)
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
                new StatModifier(1, 1, EffectImmunity.POISON, ModType.FLAT),
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
            ModRegistry.UNIQUE_GEARS.HUNGER_NECKLACE,
            "hunger_neck",
            "Eternal Hunger",
            "They say the only way to take off this necklace is to rid yourself of your mortal flesh.",
            BaseGearJewelry.HP_NECKLACE.get(LevelRanges.MID_TO_END))
            .stats(Arrays.asList(
                new StatModifier(-100, -100, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(-100, -100, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(15, 25, DamageUnderPotion.HUNGER, ModType.FLAT),
                new StatModifier(15, 50, Lifesteal.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(15, 50, MagicSteal.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(0.3F, 0.45F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

    }
}
