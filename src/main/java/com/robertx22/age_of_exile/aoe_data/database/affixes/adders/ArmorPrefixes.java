package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ArmorPrefixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("scaled")
            .Named("Scaled")
            .stats(new StatModifier(0.5F, 1.5F, Armor.getInstance(), ModType.FLAT), new StatModifier(1, 1.5F, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.armor_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("reinforced")
            .Named("Reinforced")
            .stats(new StatModifier(8, 15, Armor.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.armor_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("virile")
            .Named("Virile")
            .stats(new StatModifier(1, 3, Health.getInstance(), ModType.FLAT))

            .includesTags(SlotTag.armor_family, SlotTag.shield, SlotTag.jewelry_family)
            .Prefix()
            .Build();

    }
}
