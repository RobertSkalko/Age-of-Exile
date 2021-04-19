package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DayDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class PharaohArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        // mage/priest cloth light/nature/fire

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.PHARAOH_SET,
            ArmorSet.SlotEnum.HELMET,
            "Pharaoh",
            BaseClothArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.HELMET),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.HELMET),
                    new StatModifier(10, 25, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, new ElementalSpellDamage(Elements.Light)),
                    new StatModifier(5, 25, HealPower.getInstance(), ModType.FLAT),
                    new StatModifier(5, 15, TreasureQuantity.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -20, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(1, 10, Intelligence.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 2, Wisdom.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setInt(0.5F)
                .setWis(0.75F))
            .gearSet(GearSetsAdder.PHARAOH_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.PHARAOH_SET,
            ArmorSet.SlotEnum.CHEST,
            "Pharaoh",
            BaseClothArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.CHEST),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.CHEST),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Light))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 25, HealPower.getInstance(), ModType.FLAT),
                    new StatModifier(5, 15, TreasureQuality.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setInt(0.5F)
                .setWis(0.8F))
            .gearSet(GearSetsAdder.PHARAOH_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.PHARAOH_SET,
            ArmorSet.SlotEnum.PANTS,
            "Pharaoh",
            BaseClothArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.PANTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.PANTS),
                    new StatModifier(10, 25, new ElementalResist(Elements.Fire)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalSpellDamage(Elements.Fire)),
                    new StatModifier(20, 40, Mana.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(5, 10, TreasureQuantity.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -20, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setWis(0.5F)
                .setInt(0.8F))

            .gearSet(GearSetsAdder.PHARAOH_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.PHARAOH_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Pharaoh",
            BaseClothArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, SpellCriticalDamage.getInstance()),
                    new StatModifier(5, 10, BonusExp.getInstance()),
                    new StatModifier(5, 15, new ElementalDamageBonus(Elements.Fire)),
                    new StatModifier(5, 10, TreasureQuality.getInstance(), ModType.FLAT),
                    new StatModifier(10, 20, DayDamage.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setVit(0.3F)
                .setDex(0.25F)
                .setInt(0.8F))
            .gearSet(GearSetsAdder.PHARAOH_SET)
            .build();

    }
}

