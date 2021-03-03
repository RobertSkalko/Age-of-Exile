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

        // different ids so gear can have 3 socket prefixes etc
        AffixBuilder.Normal("jeweled_1")
            .Named("Jeweled")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("jeweled_2")
            .Named("Jeweled")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("jeweled_3")
            .Named("Jeweled")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("socket_suff_1")
            .Named("Of Sockets")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("socket_suff_2")
            .Named("Of Sockets")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("socket_suff_3")
            .Named("Of Sockets")
            .tier(1, new StatModifier(1, 1, MoreSocketsStat.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.weapon_family, SlotTag.offhand_family)
            .Suffix()
            .Build();

    }
}
