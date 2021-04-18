package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearJewelry;
import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.resources.heals.HealCritChance;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class FabledJewelry implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.AZUNA_RING,
            "azuna_ring",
            "Azuna's Eternal Decree",
            "",
            BaseGearJewelry.MANA_RING.values())
            .setFabled()
            .setReplacesName()
            .baseStats(
                new StatModifier(10, 25, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 25, new ElementalResist(Elements.Nature), ModType.FLAT),
                new StatModifier(10, 25, new ElementalResist(Elements.Light), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(10, 25, TreasureQuality.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, HealCritChance.getInstance(), ModType.FLAT),
                new StatModifier(3, 6, Intelligence.INSTANCE, ModType.FLAT),
                new StatModifier(3, 6, Agility.INSTANCE, ModType.FLAT),
                new StatModifier(5, 5, DatapackStatAdder.GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25, ModType.FLAT),
                new StatModifier(10, 10, DatapackStatAdder.GLOBAL_CRIT_DMG_PER_ITEM_FIND_25, ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setVit(0.5F)
                .setInt(0.5F)
                .setWis(0.8F))
            .devComment("God's ring: item find and luck")
            .build();

    }
}
