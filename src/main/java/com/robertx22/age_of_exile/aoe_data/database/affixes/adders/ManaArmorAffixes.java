package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ManaArmorAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
            .Named("Glimmering")
            .stats(new StatModifier(4, 10, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(4, 6, Armor.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.armor_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("seraphim")
            .Named("Seraphim's")
            .stats(new StatModifier(4, 10, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(3, 6, Health.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.armor_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("azure")
            .Named("Azure")
            .stats(new StatModifier(5, 12, Mana.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.armor_family)
            .Prefix()
            .Build();

    }
}

