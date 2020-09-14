package com.robertx22.age_of_exile.database.data.unique_items.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueJewelry implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            "angel_prot_necklace",
            "Angelic Protection",
            "If your guardian is careful enough, you just might think they don't exist.",
            BaseGearTypes.START_TO_LOW_ALL_RES_NECKLACE)
            .stats(Arrays.asList(
                new StatModifier(50, 100, ManaBurnResistance.getInstance(), ModType.FLAT),
                new StatModifier(1, 1, ImmuneToEffectStat.POISON, ModType.FLAT),
                new StatModifier(3, 5, PlusResourceOnKill.MANA, ModType.FLAT),
                new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "birth_miracle",
            "Birthing Miracle",
            "Life can sometimes keep on giving.",
            BaseGearTypes.MID_TO_END_HP_NECKLACE)
            .stats(Arrays.asList(
                new StatModifier(3, 8, Health.getInstance(), ModType.FLAT),
                new StatModifier(0.1F, 0.2F, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(2, 5, PlusResourceOnKill.HEALTH, ModType.FLAT),
                new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "skull_of_spirits",
            "Skull of Spirits",
            "The mysterious skull contains knowledge of the contained spirits, but also their madness.",
            BaseGearTypes.MID_TO_END_ALL_RES_NECKLACE)
            .stats(Arrays.asList(
                new StatModifier(0.15F, 0.4F, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(10, 20, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(-5, -15, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "greed_persist",
            "Greed's Persistence",
            "When desire for perfection overtakes your sanity, you too will be blessed.",
            BaseGearTypes.MID_TO_END_HP_RING_FIRE)
            .stats(Arrays.asList(
                new StatModifier(-30, 25, IncreasedItemQuantity.getInstance(), ModType.FLAT),
                new StatModifier(-30, 25, MagicFind.getInstance(), ModType.FLAT),
                new StatModifier(5, 10, new ElementalResist(Elements.Elemental), ModType.FLAT),
                new StatModifier(-10, -5, Health.getInstance(), ModType.FLAT)

            ))
            .build();

        UniqueGearBuilder.of(
            "loop_of_infinity",
            "Loop of Infinity",
            "Is it truly an end if everything just starts all over again? Maybe it really is just a loop.",
            BaseGearTypes.MID_TO_END_HP_RING_MANA_REG)
            .stats(Arrays.asList(
                new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT),
                new StatModifier(1, 4, RegeneratePercentStat.MANA, ModType.FLAT),
                new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();

    }

}

