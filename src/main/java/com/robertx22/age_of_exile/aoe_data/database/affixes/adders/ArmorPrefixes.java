package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ArmorPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("protective")
            .Named("Protective")
            .tier(1, new StatModifier(3, 4, Armor.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(2, 3, Armor.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 2, Armor.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(0.5F, 1F, Armor.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.armor_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("scaled")
            .Named("Scaled")
            .tier(1, new StatModifier(1F, 1.5F, Armor.getInstance(), ModType.FLAT), new StatModifier(1, 1.5F, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.8F, 1F, Armor.getInstance(), ModType.FLAT), new StatModifier(0.8F, 1F, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.5F, 0.8F, Armor.getInstance(), ModType.FLAT), new StatModifier(0.5F, 0.8F, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.armor_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("reinforced")
            .Named("Reinforced")
            .tier(1, new StatModifier(20, 25, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(18, 20, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(15, 18, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(10, 15, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.armor_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("virile")
            .Named("Virile")
            .tier(1, new StatModifier(2, 3, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(1.5F, 2, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(1, 1.5F, Health.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(0.5F, 1, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.armor_family, SlotTag.shield, SlotTag.jewelry_family)
            .Prefix()
            .Build();

    }
}
