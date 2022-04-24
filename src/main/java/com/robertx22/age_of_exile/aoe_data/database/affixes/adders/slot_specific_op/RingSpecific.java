package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import static com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;

public class RingSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("of_mana")
            .Named("Of Mana")
            .stats(new StatModifier(6, 15, Mana.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.ring)
            .Suffix()
            .Build();

        AffixBuilder.Normal("mana_reg")
            .Named("Soothing")
            .stats(new StatModifier(6, 15, ManaRegen.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.ring)
            .Prefix()
            .Build();

    }
}
