package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.plate;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CooldownReduction;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class OakArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        // nature/light heavy armor, focuses on healing

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.OAK_SET,
            ArmorSet.SlotEnum.HELMET,
            "Great Oak",
            BasePlateArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.HELMET),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.HELMET),
                    new StatModifier(20, 40, new ElementalResist(Elements.Nature)),
                    new StatModifier(-20, -20, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 25, HealPower.getInstance(), ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.OAK_SET)
            .req(new StatRequirement()
                .setStr(0.2F)
                .setWis(0.5F)
                .setVit(0.3F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.OAK_SET,
            ArmorSet.SlotEnum.CHEST,
            "Great Oak",
            BasePlateArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.CHEST),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.CHEST),
                    new StatModifier(15, 25, new ElementalResist(Elements.Nature)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Light))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.OAK_SET)
            .req(new StatRequirement()
                .setStr(0.5F)
                .setWis(0.25F)
                .setVit(0.8F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.OAK_SET,
            ArmorSet.SlotEnum.PANTS,
            "Great Oak",
            BasePlateArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.PANTS),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.PANTS),
                    new StatModifier(10, 20, new ElementalResist(Elements.Light)),
                    new StatModifier(10, 20, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 15, new ElementalDamageBonus(Elements.Light), ModType.FLAT),
                    new StatModifier(5, 15, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(5, 10, CriticalHit.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.OAK_SET)
            .req(new StatRequirement()
                .setStr(0.2F)
                .setWis(0.5F)
                .setVit(0.3F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.OAK_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Great Oak",
            BasePlateArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.BOOTS),
                    getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.BOOTS),
                    new StatModifier(20, 40, new ElementalResist(Elements.Light)),
                    new StatModifier(-20, -20, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 25, new ElementalDamageBonus(Elements.Nature), ModType.FLAT),
                    new StatModifier(5, 15, AttackSpeed.getInstance(), ModType.FLAT),
                    new StatModifier(5, 10, CooldownReduction.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.OAK_SET)
            .req(new StatRequirement()
                .setStr(0.5F)
                .setVit(0.3F))
            .build();

    }
}
