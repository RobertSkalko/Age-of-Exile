package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.plate;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class DarkCrystalArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        // dark/light heal heavy armor night/day effect

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.DARK_CRYSTAL_SET,
            ArmorSet.SlotEnum.HELMET,
            "Dark Crystal",
            BasePlateArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.HELMET),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.HELMET),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, new ElementalSpellDamage(Elements.Dark)),
                    new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(5, 15, TreasureQuality.getInstance(), ModType.FLAT),
                    new StatModifier(1, 10, Intelligence.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 2, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setInt(0.4F)
                .setStr(0.4F)
            )
            .gearSet(GearSetsAdder.DARK_CRYSTAL_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.DARK_CRYSTAL_SET,
            ArmorSet.SlotEnum.CHEST,
            "Dark Crystal",
            BasePlateArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.CHEST),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.CHEST),
                    new StatModifier(15, 25, new ElementalResist(Elements.Light)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 20, HealPower.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, SpellCriticalHit.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT)

                )
            )

            .req(new StatRequirement()
                .setInt(0.4F)
                .setStr(0.4F)
            )
            .gearSet(GearSetsAdder.DARK_CRYSTAL_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.DARK_CRYSTAL_SET,
            ArmorSet.SlotEnum.PANTS,
            "Dark Crystal",
            BasePlateArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.PANTS),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.PANTS),
                    new StatModifier(15, 25, new ElementalResist(Elements.Light)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, new ElementalSpellDamage(Elements.Light)),
                    new StatModifier(10, 20, Mana.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )

            .req(new StatRequirement()
                .setInt(0.4F)
                .setStr(0.4F)
            )
            .gearSet(GearSetsAdder.DARK_CRYSTAL_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.DARK_CRYSTAL_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Dark Crystal",
            BasePlateArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.BOOTS),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.BOOTS),
                    new StatModifier(15, 25, new ElementalResist(Elements.Light))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, SpellCriticalDamage.getInstance()),
                    new StatModifier(5, 10, new ElementalDamageBonus(Elements.Dark)),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Agility.INSTANCE, ModType.FLAT)
                )
            )

            .req(new StatRequirement()
                .setInt(0.4F)
                .setStr(0.4F)
            )
            .gearSet(GearSetsAdder.DARK_CRYSTAL_SET)
            .build();

    }
}

