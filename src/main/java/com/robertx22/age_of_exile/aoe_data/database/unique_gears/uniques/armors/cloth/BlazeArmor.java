package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class BlazeArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        // DARK/FIRE cloth set

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BLAZE_SET,
            ArmorSet.SlotEnum.HELMET,
            "Blaze",
            BaseClothArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.HELMET),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.HELMET),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
                    new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(1, 10, Intelligence.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 2, Agility.INSTANCE, ModType.FLAT)
                )
            )

            .req(new StatRequirement()
                .setInt(0.2F)
                .setDex(0.25F)
                .setWis(0.6F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BLAZE_SET,
            ArmorSet.SlotEnum.CHEST,
            "Blaze",
            BaseClothArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.CHEST),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.CHEST),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, Stats.ACCURACY.get(), ModType.FLAT),
                    new StatModifier(2, 5, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setInt(0.25F)
                .setWis(0.8F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BLAZE_SET,
            ArmorSet.SlotEnum.PANTS,
            "Blaze",
            BaseClothArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.PANTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.PANTS),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
                    new StatModifier(10, 20, Mana.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setWis(0.25F)
                .setVit(0.25F)
                .setInt(0.8F))

            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.BLAZE_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Blaze",
            BaseClothArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    getStat(ArmorStat.HEALTH, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    getStat(ArmorStat.ARMOR, ArmorType.LIGHT, ArmorSlot.BOOTS),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, Stats.ELE_DOT_DAMAGE.get(Elements.Fire)),
                    new StatModifier(5, 10, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .req(new StatRequirement()
                .setVit(0.3F)
                .setDex(0.25F)
                .setInt(0.8F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

    }
}

