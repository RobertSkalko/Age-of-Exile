package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.tag.EntityTypeTags;

import java.util.Arrays;

public class BoneArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BONE_SET,
            ArmorSet.SlotEnum.CHEST,
            "Bone",
            BaseLeatherArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(10, 20, DodgeRating.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Fire)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, ArmorPenetration.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, CriticalHit.getInstance(), ModType.FLAT),
                    new StatModifier(5, 8, new SpecificWeaponDamage(WeaponTypes.Bow), ModType.FLAT),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)

                )
            )
            .mobTagFilter(EntityTypeTags.SKELETONS)
            .req(new StatRequirement()
                .setDex(0.25F)
                .setAgi(0.8F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BONE_SET,
            ArmorSet.SlotEnum.HELMET,
            "Bone",
            BaseLeatherArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(3, 3, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 10, DodgeRating.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalSpellDamage(Elements.Water)),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .mobTagFilter(EntityTypeTags.SKELETONS)

            .req(new StatRequirement()
                .setInt(0.2F)
                .setDex(0.25F)
                .setAgi(0.6F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BONE_SET,
            ArmorSet.SlotEnum.PANTS,
            "Bone",
            BaseLeatherArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 10, DodgeRating.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Water))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalSpellDamage(Elements.Dark)),
                    new StatModifier(10, 20, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .mobTagFilter(EntityTypeTags.SKELETONS)

            .req(new StatRequirement()
                .setDex(0.25F)
                .setAgi(0.8F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BONE_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Bone",
            BaseLeatherArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(4, 4, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 10, DodgeRating.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Fire)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, CriticalDamage.getInstance()),
                    new StatModifier(15, 25, SpellCriticalDamage.getInstance()),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .mobTagFilter(EntityTypeTags.SKELETONS)

            .req(new StatRequirement()
                .setInt(0.3F)
                .setDex(0.25F)
                .setAgi(0.8F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

    }
}
