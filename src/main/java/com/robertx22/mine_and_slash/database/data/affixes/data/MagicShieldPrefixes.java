package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Mana;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class MagicShieldPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
            .Named("Glimmering")
            .tier(1, new StatModifier(6, 8, MagicShield.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(4, 6, MagicShield.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(2, 4, MagicShield.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(MagicShield.getInstance())
                .plus(x -> x.family()
                    .isJewelry()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("seraphim")
            .Named("Seraphim's")
            .tier(1, new StatModifier(3, 4, MagicShield.getInstance(), ModType.FLAT), new StatModifier(3, 5, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, MagicShield.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(MagicShield.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("resolute")
            .Named("Resolute")
            .tier(1, new StatModifier(25F, 50F, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(20F, 25F, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 20F, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(5, 10, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .Req(SlotRequirement.hasBaseStat(MagicShield.getInstance()))
            .Prefix()
            .Build();

        AffixBuilder.Normal("azure")
            .Named("Azure")
            .tier(1, new StatModifier(8, 12, Mana.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(6, 8, Mana.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(4, 6, Mana.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(2, 4, Mana.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.hasBaseStat(MagicShield.getInstance()))
            .Prefix()
            .Build();

    }
}

