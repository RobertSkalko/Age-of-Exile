package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.misc.DamageTakenToMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilterData;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.MobTagFilter;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.tag.EntityTypeTags;

import java.util.Arrays;

public class BoneArmor implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.OAK_CHEST,
            "bone_chest",
            "Bone",
            "",
            BaseLeatherArmors.CHESTS.get(LevelRanges.MIDDLE))
            .baseStats(
                Arrays.asList(
                    new StatModifier(10, 10, Health.getInstance(), ModType.FLAT),
                    new StatModifier(10, 20, Armor.getInstance(), ModType.FLAT),
                    new StatModifier(15, 25, new ElementalResist(Elements.Nature)),
                    new StatModifier(15, 25, new ElementalResist(Elements.Thunder))
                )
            )
            .stats(
                Arrays.asList(
                    new StatModifier(5, 10, HealthRegen.getInstance(), ModType.FLAT),
                    new StatModifier(1, 3, DamageTakenToMana.getInstance(), ModType.FLAT),
                    new StatModifier(2, 5, Wisdom.INSTANCE, ModType.FLAT),
                    new StatModifier(2, 5, Vitality.INSTANCE, ModType.FLAT)
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
