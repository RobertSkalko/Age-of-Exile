package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
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
            .add(Elements.Nature, "Dripping")
            .add(Elements.Light, "Shinning")
            .add(Elements.Dark, "Ominous")
            .tier(1, x -> Arrays.asList(new StatModifier(0.6F, 0.7F, 0.7F, 0.8F, new AttackDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(0.4F, 0.5F, 0.5F, 0.6F, new AttackDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(0.2F, 0.3F, 0.3F, 0.4F, new AttackDamage(x), ModType.FLAT)))
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

        AffixBuilder.Normal("vampiric")
            .Named("Vampiric")
            .Weight(100)
            .tier(1, new StatModifier(1, 2, Stats.LIFESTEAL.get(), ModType.FLAT))
            .tier(2, new StatModifier(1, 1, Stats.LIFESTEAL.get(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("scavanger")
            .Named("Scavanger")
            .tier(1, new StatModifier(6, 8, Stats.RESOURCE_ON_KILL.get(ResourceType.health)))
            .tier(2, new StatModifier(4, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.health)))
            .tier(3, new StatModifier(3, 4, Stats.RESOURCE_ON_KILL.get(ResourceType.health)))
            .tier(4, new StatModifier(1, 3, Stats.RESOURCE_ON_KILL.get(ResourceType.health)))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("mana_thief")
            .Named("Mana Thief's")
            .tier(1, new StatModifier(6, 8, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)))
            .tier(2, new StatModifier(4, 6, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)))
            .tier(3, new StatModifier(3, 4, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)))
            .tier(4, new StatModifier(1, 3, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)))
            .includesTags(SlotTag.jewelry_family)
            .Prefix()
            .Build();

    }
}
