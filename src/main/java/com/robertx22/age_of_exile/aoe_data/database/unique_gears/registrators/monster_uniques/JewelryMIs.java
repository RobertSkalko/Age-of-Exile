package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.monster_uniques;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilterData;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.MobTagFilter;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.tag.EntityTypeTags;

import java.util.Arrays;

public class JewelryMIs implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BONECHILL_RING_MI,
            "bonechill_ring",
            "Bonechill Ring",
            "",
            BaseGearJewelry.COLD_RES_RING.values())
            .stats(Arrays.asList(
                new StatModifier(2, 3, Health.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, new ElementalDamageBonus(Elements.Water), ModType.FLAT)
            ))
            .setMI()
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BONECHILL_AMULET_MI,
            "bonechill_amulet",
            "Bonechill Amulet",
            "",
            BaseGearJewelry.ALL_RES_NECKLACE.values())
            .stats(Arrays.asList(
                new StatModifier(3, 4, Health.getInstance(), ModType.FLAT),
                new StatModifier(7, 18, new ElementalDamageBonus(Elements.Water), ModType.FLAT)
            ))
            .setMI()
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))
            .build();

    }
}
