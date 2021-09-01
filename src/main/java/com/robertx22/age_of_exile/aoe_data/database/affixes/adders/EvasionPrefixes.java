package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class EvasionPrefixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("agile")
            .Named("Agile")
            .stats(new StatModifier(0.5F, 4, DodgeRating.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("fawns")
            .Named("Fawn's")
            .stats(new StatModifier(1.3F, 1.5F, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(0.5F, 2, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("shades")
            .Named("Shade's")
            .stats(new StatModifier(8, 20, DodgeRating.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

    }
}