package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.loot.ItemFind;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class JewelrySuffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_ele_dmg_jewelry")
            .add(Elements.Fire, "Of Embers")
            .add(Elements.Water, "Of Ice")
            .add(Elements.Earth, "Of Venom")
            .stats(x -> Arrays.asList(new StatModifier(3, 6, Stats.ELEMENTAL_DAMAGE.get(x), ModType.FLAT)))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_philosopher")
            .Named("Of the Philosopher")
            .coreStat(DatapackStats.INT)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_titan")
            .Named("Of the Titan")
            .coreStat(DatapackStats.STR)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_wind")
            .Named("Of the Wind")
            .coreStat(DatapackStats.DEX)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_wise")
            .Named("Of the Wise")
            .coreStat(DatapackStats.WIS)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_agile")
            .Named("Of Agility")
            .coreStat(DatapackStats.AGI)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_virile")
            .Named("Of the Virile")
            .coreStat(DatapackStats.VIT)
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_sky")
            .Named("Of the Sky")
            .stats(new StatModifier(0.1F, 0.4F, AllAttributes.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Weight(50)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_troll")
            .Named("Of The Troll")
            .stats(new StatModifier(3, 6, HealthRegen.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_spirit_markings")
            .Named("Of Spirit Markings")
            .stats(new StatModifier(3, 6, ManaRegen.getInstance(), ModType.PERCENT))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family)
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_treasure")
            .Named("Of Treasure")
            .stats(new StatModifier(5, 15F, MagicFind.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_affluence")
            .Named("Of Affluence")
            .stats(new StatModifier(3, 10, ItemFind.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

    }
}
