package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.DoubleDropChance;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.TripleDropChance;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class AllToolAffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("double_drop_chance_pre")
            .Named("Plentiful")
            .tier(1, new StatModifier(9, 12, DoubleDropChance.getInstance()))
            .tier(2, new StatModifier(7, 9, DoubleDropChance.getInstance()))
            .tier(3, new StatModifier(3, 7, DoubleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .AllowDuplicatesOnSameItem()
            .Prefix()
            .Build();

        AffixBuilder.Normal("double_drop_chance_suf")
            .Named("Of Duplication")
            .tier(1, new StatModifier(9, 12, DoubleDropChance.getInstance()))
            .tier(2, new StatModifier(7, 9, DoubleDropChance.getInstance()))
            .tier(3, new StatModifier(3, 7, DoubleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .AllowDuplicatesOnSameItem()
            .Suffix()
            .Build();

        AffixBuilder.Normal("triple_drop_chance_pre")
            .Named("Bountiful")
            .tier(1, new StatModifier(8, 10, TripleDropChance.getInstance()))
            .tier(2, new StatModifier(6, 8, TripleDropChance.getInstance()))
            .tier(3, new StatModifier(2, 6, TripleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .AllowDuplicatesOnSameItem()
            .Prefix()
            .Build();

        AffixBuilder.Normal("triple_drop_chance_suf")
            .Named("Of Triplication")
            .tier(1, new StatModifier(8, 10, TripleDropChance.getInstance()))
            .tier(2, new StatModifier(6, 8, TripleDropChance.getInstance()))
            .tier(3, new StatModifier(2, 6, TripleDropChance.getInstance()))
            .includesTags(SlotTag.tool_family)
            .AllowDuplicatesOnSameItem()
            .Suffix()
            .Build();

    }
}
