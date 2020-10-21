package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class JewelryPrefixes implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg_jewelry")
            .add(Elements.Fire, "Smoking")
            .add(Elements.Water, "Freezing")
            .add(Elements.Thunder, "Shocking")
            .add(Elements.Nature, "Dripping")
            .tier(1, x -> Arrays.asList(new StatModifier(1, 2, 1, 2, new AttackDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(0.75F, 1, 0.75F, 1, new AttackDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(0.5F, 0.75F, 0.5F, 0.75F, new AttackDamage(x), ModType.FLAT)))
            .includesTags(SlotTag.jewelry_family)
            .Weight(100)
            .Prefix()
            .Build();

        AffixBuilder.Normal("archeologists")
            .Named("Archeologist's")
            .tier(1, new StatModifier(10, 15F, TreasureQuality.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(7, 10, TreasureQuality.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 7, TreasureQuality.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

    }
}
