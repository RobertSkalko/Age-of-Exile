package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class JewelryPrefixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("archeologists")
            .Named("Archeologist's")
            .stats(new StatModifier(5, 15F, MagicFind.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("vampiric")
            .Named("Vampiric")
            .Weight(100)
            .stats(new StatModifier(1, 2, Stats.LIFESTEAL.get(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("scavanger")
            .Named("Scavanger")
            .stats(new StatModifier(2, 8, Stats.RESOURCE_ON_KILL.get(ResourceType.health)))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("mana_thief")
            .Named("Mana Thief's")
            .stats(new StatModifier(2, 8, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

    }
}
