package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilterData;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.MobTagFilter;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.tag.EntityTypeTags;

import java.util.Arrays;

public class BoneArmor implements ISlashRegistryInit {

    public static String CHEST_ID = "bone_chest";
    public static String PANTS_ID = "bone_pants";
    public static String HELMET_ID = "bone_helmet";
    public static String BOOTS_ID = "bone_boots";

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BONE_CHEST,
            CHEST_ID,
            "Bone",
            "",
            BaseLeatherArmors.CHESTS.get(LevelRanges.MIDDLE))
            .baseStats(
                Arrays.asList(
                    new StatModifier(5, 5, Health.getInstance(), ModType.FLAT),
                    new StatModifier(10, 20, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(15, 25, new ElementalResist(Elements.Water)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
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
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))

            .req(new StatRequirement()
                .setDex(0.25F)
                .setAgi(0.8F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BONE_HELMET,
            HELMET_ID,
            "Bone",
            "",
            BaseLeatherArmors.HELMETS.get(LevelRanges.MIDDLE))
            .baseStats(
                Arrays.asList(
                    new StatModifier(3, 3, Health.getInstance(), ModType.FLAT),
                    new StatModifier(5, 10, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(15, 25, new ElementalResist(Elements.Dark))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(15, 25, new ElementalSpellDamage(Elements.Water)),
                    new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)
                )
            )
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))

            .req(new StatRequirement()
                .setDex(0.25F)
                .setAgi(0.8F))
            .gearSet(GearSetsAdder.BONE_SET)
            .build();

    }
}
