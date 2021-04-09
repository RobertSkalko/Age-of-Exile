package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseClothArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DamageOverTime;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.EntityType;

import java.util.Arrays;

public class BlazeArmor implements ISlashRegistryInit {

    public static String CHEST_ID = "blaze_chest";
    public static String PANTS_ID = "blaze_pants";
    public static String HELMET_ID = "blaze_helmet";
    public static String BOOTS_ID = "blaze_boots";

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BLAZE_HELMET,
            HELMET_ID,
            "Blaze",
            "",
            BaseClothArmors.HELMETS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(3, 3, Health.getInstance(), ModType.FLAT),
                    new StatModifier(2, 8, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, new ElementalSpellDamage(Elements.Fire)),
                    new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(1, 10, Intelligence.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 2, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .mobFilter(EntityType.BLAZE)

            .req(new StatRequirement()
                .setInt(0.2F)
                .setDex(0.25F)
                .setWis(0.6F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BLAZE_CHEST,
            CHEST_ID,
            "Blaze",
            "",
            BaseClothArmors.CHESTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 15, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, Accuracy.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, SpellCriticalHit.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Wisdom.INSTANCE, ModType.FLAT)

                )
            )
            .mobFilter(EntityType.BLAZE)

            .req(new StatRequirement()
                .setInt(0.25F)
                .setWis(0.8F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BLAZE_PANTS,
            PANTS_ID,
            "Blaze",
            "",
            BaseClothArmors.PANTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 12, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Light)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Fire))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalSpellDamage(Elements.Dark)),
                    new StatModifier(10, 20, Mana.getInstance(), ModType.LOCAL_INCREASE),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
                )
            )
            .mobFilter(EntityType.BLAZE)

            .req(new StatRequirement()
                .setWis(0.25F)
                .setVit(0.25F)
                .setInt(0.8F))

            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BLAZE_BOOTS,
            BOOTS_ID,
            "Blaze",
            "",
            BaseClothArmors.BOOTS.values())
            .baseStats(
                Arrays.asList(
                    new StatModifier(4, 4, Health.getInstance(), ModType.FLAT),
                    new StatModifier(3, 8, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(-10, -10, new ElementalResist(Elements.Water)),
                    new StatModifier(20, 30, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(10, 20, new DamageOverTime(Elements.Fire)),
                    new StatModifier(5, 10, new ElementalDamageBonus(Elements.Dark)),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(1, 10, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .mobFilter(EntityType.BLAZE)

            .req(new StatRequirement()
                .setVit(0.3F)
                .setDex(0.25F)
                .setInt(0.8F))
            .gearSet(GearSetsAdder.BLAZE_SET)
            .build();

    }
}

