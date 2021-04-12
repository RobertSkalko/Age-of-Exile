package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class VoidArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        // cloth mage focused dark armor, but also for assasins

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.VOID_SET,
            ArmorSet.SlotEnum.HELMET,
            "Eyes of the Void",
            BaseClothArmors.HELMETS.values())
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.HELMET),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.HELMET),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 25, Mana.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(5, 15, new ElementalPenetration(Elements.Dark), ModType.FLAT),
                    new StatModifier(1, 10, Intelligence.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 2, Wisdom.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setInt(0.7F)
                .setStr(0.2F)
            )
            .gearSet(GearSetsAdder.VOID_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.VOID_SET,
            ArmorSet.SlotEnum.CHEST,
            "Void Seeker",
            BaseClothArmors.CHESTS.values())
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.CHEST),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.CHEST),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 15, AttackStyleDamage.MELEE, ModType.FLAT),
                    new StatModifier(5, 10, Accuracy.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(1, 10, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Strength.INSTANCE, ModType.FLAT)

                )
            )

            .req(new StatRequirement()
                .setInt(0.2F)
                .setStr(0.75F)
            )
            .gearSet(GearSetsAdder.VOID_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.VOID_SET,
            ArmorSet.SlotEnum.PANTS,
            "Carrier of the Void",
            BaseClothArmors.PANTS.values())
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.PANTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.PANTS),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, ArmorPenetration.getInstance()),
                    new StatModifier(10, 20, Health.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(5, 10, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )

            .req(new StatRequirement()
                .setInt(0.2F)
                .setStr(0.75F)
            )
            .gearSet(GearSetsAdder.VOID_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.VOID_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Void Walker",
            BaseClothArmors.BOOTS.values())
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, SpellCriticalHit.getInstance()),
                    new StatModifier(5, 15, new ElementalSpellDamage(Elements.Dark)),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Agility.INSTANCE, ModType.FLAT)
                )
            )

            .req(new StatRequirement()
                .setInt(0.75F)
                .setStr(0.2F)
            )
            .gearSet(GearSetsAdder.VOID_SET)
            .build();

    }
}

