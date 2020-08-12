package com.robertx22.age_of_exile.database.data.affixes.data;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class EvasionPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("agile")
            .Named("Agile")
            .tier(1, new StatModifier(30, 40, DodgeRating.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(20, 30, DodgeRating.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(10, 20, DodgeRating.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(5, 10, DodgeRating.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat, SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("fawns")
            .Named("Fawn's")
            .tier(1, new StatModifier(12, 15, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(8, 12, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(3, 8, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(1, 1, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("shades")
            .Named("Shade's")
            .tier(1, new StatModifier(25F, 50F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(5, 10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.dodge_stat)
            .Prefix()
            .Build();

    }
}