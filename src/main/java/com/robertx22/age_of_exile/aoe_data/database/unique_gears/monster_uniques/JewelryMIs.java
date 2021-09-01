package com.robertx22.age_of_exile.aoe_data.database.unique_gears.monster_uniques;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilterData;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.MobTagFilter;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.tag.EntityTypeTags;

import java.util.Arrays;

public class JewelryMIs implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.BONECHILL_RING_MI,
                "bonechill_ring",
                "Bonechill Ring",
                "",
                BaseGearJewelry.MANA_RING.values())
            .stats(Arrays.asList(
                new StatModifier(5, 10, Health.getInstance(), ModType.PERCENT),
                new StatModifier(5, 10, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(5, 15, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT)
            ))
            .req(new StatRequirement().setWis(0.5F)
                .setAgi(0.25F))
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
            .req(new StatRequirement().setWis(0.5F)
                .setAgi(0.25F))
            .stats(Arrays.asList(
                new StatModifier(5, 12, Health.getInstance(), ModType.PERCENT),
                new StatModifier(5, 12, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(7, 18, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT)
            ))
            .setMI()
            .dropFilter(DropFilterData.of(new MobTagFilter(), EntityTypeTags.SKELETONS.getId()
                .toString()))
            .build();

    }
}
