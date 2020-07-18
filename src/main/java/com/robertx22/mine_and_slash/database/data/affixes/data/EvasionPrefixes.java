package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class EvasionPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("agile")
            .Named("Agile")
            .tier(1, new StatModifier(30, 40, DodgeRating.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(20, 30, DodgeRating.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(10, 20, DodgeRating.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(5, 10, DodgeRating.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(DodgeRating.getInstance())
                .plus(x -> x.family()
                    .isJewelry()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("fawns")
            .Named("Fawn's")
            .tier(1, new StatModifier(4, 6, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(3, 5, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(3, 4, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(2, 3, DodgeRating.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(DodgeRating.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("shades")
            .Named("Shade's")
            .tier(1, new StatModifier(25F, 50F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(5, 10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE))
            .Req(SlotRequirement.hasBaseStat(DodgeRating.getInstance()))
            .Prefix()
            .Build();

    }
}