package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class MagicShieldPrefixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
            .Named("Glimmering")
            .tier(1, new StatModifier(15, 20, Mana.getInstance(), ModType.FLAT), new StatModifier(10, 15, Armor.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, Mana.getInstance(), ModType.FLAT), new StatModifier(8, 10, Armor.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT), new StatModifier(5, 8, Armor.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("seraphim")
            .Named("Seraphim's")
            .tier(1, new StatModifier(15, 20, Mana.getInstance(), ModType.FLAT), new StatModifier(7, 10, Health.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(10, 15, Mana.getInstance(), ModType.FLAT), new StatModifier(4, 7, Health.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT), new StatModifier(2, 4, Health.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

        AffixBuilder.Normal("azure")
            .Named("Azure")
            .tier(1, new StatModifier(20, 25, Mana.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(15, 20, Mana.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(10, 15, Mana.getInstance(), ModType.FLAT))
            .tier(4, new StatModifier(5, 10, Mana.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.magic_shield_stat)
            .Prefix()
            .Build();

    }
}

