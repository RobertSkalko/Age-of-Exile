package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import static com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;

public class ArmorSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("strong_int_armor_suf")
            .Named("Of the Genius")
            .bigCoreStat(DatapackStats.INT)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();

        AffixBuilder.Normal("strong_str_armor_suf")
            .Named("Of the Monster")
            .bigCoreStat(DatapackStats.STR)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();

        AffixBuilder.Normal("strong_dex_armor_suf")
            .Named("Of Skill")
            .bigCoreStat(DatapackStats.DEX)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();

        AffixBuilder.Normal("strong_wis_armor_suf")
            .Named("Of the Wise")
            .bigCoreStat(DatapackStats.WIS)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();

        AffixBuilder.Normal("strong_agi_armor_suf")
            .Named("Of Fortitude")
            .bigCoreStat(DatapackStats.AGI)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();

        AffixBuilder.Normal("strong_vit_armor_suf")
            .Named("Of the Titan")
            .bigCoreStat(DatapackStats.VIT)
            .includesTags(SlotTag.armor_family)
            .Suffix()
            .Weight(10000)
            .Build();
    }
}
