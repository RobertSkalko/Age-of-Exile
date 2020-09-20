package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class MagicShieldPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
            .Named("Glimmering")
            .tier(1, new StatModifier(3, 4, MagicShield.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, MagicShield.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(0, 1, MagicShield.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("seraphim")
            .Named("Seraphim's")
            .tier(1, new StatModifier(2, 3, MagicShield.getInstance(), ModType.FLAT), new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(1, 2, MagicShield.getInstance(), ModType.FLAT), new StatModifier(1, 2, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.5F, 1, MagicShield.getInstance(), ModType.FLAT), new StatModifier(0.5F, 1, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("resolute")
            .Named("Resolute")
            .tier(1, new StatModifier(30, 40, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(25, 30, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(20, 25, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(10, 20, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(5, 10, MagicShield.getInstance(), ModType.LOCAL_INCREASE))
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

