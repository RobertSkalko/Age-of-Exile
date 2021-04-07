package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
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

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BONE_CHEST,
            "bone_chest",
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
                    new StatModifier(5, 5, SpecialStats.CRIT_BURN, ModType.FLAT),
                    new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT)

                )
            )
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))

            .req(new StatRequirement()
                .setDex(0.25F)
                .setAgi(0.8F))
            .build();

    }
}
