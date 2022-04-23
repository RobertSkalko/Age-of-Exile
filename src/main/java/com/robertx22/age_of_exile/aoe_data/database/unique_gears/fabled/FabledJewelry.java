package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class FabledJewelry implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "azuna_ring",
                "Azuna's Eternal Decree",
                BaseGearKeys.RING)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(10, 15, new ElementalResist(Elements.Fire), ModType.PERCENT),
                new StatModifier(10, 15, new ElementalResist(Elements.Earth), ModType.PERCENT),
                new StatModifier(5, 5, DatapackStats.GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25, ModType.FLAT),
                new StatModifier(10, 10, DatapackStats.GLOBAL_CRIT_DMG_PER_ITEM_FIND_25, ModType.FLAT)
            ))

            .devComment("God's ring: item find and luck")
            .build();

    }
}
