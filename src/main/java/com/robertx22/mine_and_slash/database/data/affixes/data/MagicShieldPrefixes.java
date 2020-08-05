package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType.SlotTag;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Mana;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class MagicShieldPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
            .Named("Glimmering")
            .tier(1, new StatModifier(5, 6, MagicShield.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(4, 5, MagicShield.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(3, 4, MagicShield.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("seraphim")
            .Named("Seraphim's")
            .tier(1, new StatModifier(3, 4, MagicShield.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, MagicShield.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT), new StatModifier(1, 1, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("resolute")
            .Named("Resolute")
            .tier(1, new StatModifier(40, 50, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(30, 40, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(20, 30, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(15, 20, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(5, 15, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("azure")
            .Named("Azure")
            .tier(1, new StatModifier(8, 12, Mana.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(6, 8, Mana.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(4, 6, Mana.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(2, 4, Mana.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

    }
}

