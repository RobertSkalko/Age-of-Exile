package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class EvasionPrefixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("agile")
            .Named("Agile")
            .tier(1, new StatModifier(3, 4, DodgeRating.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, DodgeRating.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, DodgeRating.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(0.5F, 1, DodgeRating.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("fawns")
            .Named("Fawn's")
            .tier(1, new StatModifier(1.2F, 1.5F, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(1.5F, 2, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.8F, 1.2F, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(1, 1.5F, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.8F, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(0.5F, 1, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("shades")
            .Named("Shade's")
            .tier(1, new StatModifier(22, 25, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20, 22, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(8, 10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

    }
}