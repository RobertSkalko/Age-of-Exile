package com.robertx22.age_of_exile.database.data.unique_items.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.HealToSpellDmgStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueWeapons implements ISlashRegistryInit {
    @Override
    public void registerAll() {
        UniqueGearBuilder.of(
            "obsi_might",
            "Obsidian's Might",
            "This thirst for blood only seems lower than it's desire to taste burning flesh.",
            BaseGearTypes.END_AXE)
            .stats(Arrays.asList(
                new StatModifier(3, 5, 5, 12, new WeaponDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(15, 50, CriticalHit.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(1, 3, PlusResourceOnKill.HEALTH, ModType.FLAT),
                new StatModifier(15, 30, BonusDmgToStatusAffected.BURN, ModType.FLAT),
                new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "inca_thunder",
            "Incarnation of Thunder",
            "The ability to wield thunder is said to come once in a millennium.",
            BaseGearTypes.END_SWORD)
            .stats(Arrays.asList(
                new StatModifier(1, 1, 10, 16, new WeaponDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(5, 15, ChanceToApplyEffect.STATIC, ModType.FLAT),
                new StatModifier(-20, 20, CriticalDamage.getInstance(), ModType.FLAT),
                new StatModifier(-200, -10, new ReducedAllStatReqOnItem(), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "water_ele_sword",
            "Water Elemental",
            "Essence lies in water.",
            BaseGearTypes.HIGH_SWORD)
            .stats(Arrays.asList(
                new StatModifier(1, 3, 4, 12, new WeaponDamage(Elements.Water), ModType.FLAT),
                new StatModifier(15, 30, new WeaponDamage(Elements.Water), ModType.LOCAL_INCREASE),
                new StatModifier(15, 30, BonusDmgToStatusAffected.FROST, ModType.FLAT),
                new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "eye_of_zegrath",
            "Eye of Zegrath",
            "A cultist once so powerful, even his remnant eye inflicts ills on others.",
            BaseGearTypes.MID_WAND)
            .stats(Arrays.asList(
                new StatModifier(10, 30, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE),
                new StatModifier(5, 10, ChanceToApplyEffect.BURN, ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.CHILL, ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.POISON, ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "divine_might",
            "Divine Might",
            "God might prefer peaceful means, but sometimes peace is not an option.",
            BaseGearTypes.END_SCEPTER)
            .stats(Arrays.asList(
                new StatModifier(1, 3, 3, 5, new WeaponDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(5, 25, HealPower.getInstance(), ModType.FLAT),
                new StatModifier(1, 1, HealToSpellDmgStat.getInstance(), ModType.FLAT),
                new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            "will_of_flora",
            "Will of Flora",
            "Everything must be exactly as required.",
            BaseGearTypes.HIGH_SCEPTER)
            .stats(Arrays.asList(
                new StatModifier(5, 5, 5, 5, new WeaponDamage(Elements.Nature), ModType.FLAT),
                new StatModifier(10, 30, HealPower.getInstance(), ModType.FLAT),
                new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT),
                new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
            ))
            .build();

    }
}
