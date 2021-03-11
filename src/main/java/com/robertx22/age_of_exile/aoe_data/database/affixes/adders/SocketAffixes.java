package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.misc.MoreSocketsStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class SocketAffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("jeweled")
            .Named("Jeweled")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .AllowDuplicatesOnSameItem()
            .Prefix()
            .Build();

        AffixBuilder.Normal("socket_suff")
            .Named("Of Sockets")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .AllowDuplicatesOnSameItem()
            .Suffix()
            .Build();

    }
}
