package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ArmorSet;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class SlimeArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.SLIME_SET,
            ArmorSet.SlotEnum.HELMET,
            "Slime",
            BasePlateArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(3, 10, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(10, 15, new ElementalResist(Elements.Fire)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Water)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 25, HealPower.getInstance(), ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.SLIME_SET)
            .req(new StatRequirement()
                .setStr(0.25F)
                .setInt(0.25F)
                .setDex(0.25F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.SLIME_SET,
            ArmorSet.SlotEnum.CHEST,
            "Slime",
            BasePlateArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(20, 20, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 15, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(10, 15, new ElementalResist(Elements.Fire)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Water)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 25, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(1, 10, CriticalDamage.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.SLIME_SET)
            .req(new StatRequirement()
                .setStr(0.25F)
                .setInt(0.25F)
                .setDex(0.25F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.SLIME_SET,
            ArmorSet.SlotEnum.PANTS,
            "Slime",
            BasePlateArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(15, 15, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 15, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(10, 15, new ElementalResist(Elements.Fire)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Water)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 15, AttackSpeed.getInstance(), ModType.FLAT),
                    new StatModifier(5, 25, Mana.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Intelligence.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.SLIME_SET)
            .req(new StatRequirement()
                .setStr(0.25F)
                .setInt(0.25F)
                .setDex(0.25F))
            .build();

        UniqueGearBuilder.ofSet(
            ModRegistry.UNIQUE_GEARS.SLIME_SET,
            ArmorSet.SlotEnum.BOOTS,
            "Slime",
            BasePlateArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(3, 10, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(10, 15, new ElementalResist(Elements.Fire)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Water)),
                    new StatModifier(10, 15, new ElementalResist(Elements.Nature))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 25, CriticalDamage.getInstance(), ModType.FLAT),
                    new StatModifier(1, 10, SpellDamage.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .gearSet(GearSetsAdder.SLIME_SET)
            .req(new StatRequirement()
                .setStr(0.25F)
                .setInt(0.25F)
                .setDex(0.25F))
            .build();

    }
}
