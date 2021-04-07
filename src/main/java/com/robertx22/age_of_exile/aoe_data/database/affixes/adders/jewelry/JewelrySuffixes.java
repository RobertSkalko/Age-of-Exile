package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class JewelrySuffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_ele_dmg_jewelry")
            .add(Elements.Fire, "Of Embers")
            .add(Elements.Water, "Of Ice")
            .add(Elements.Nature, "Of Venom")
            .add(Elements.Light, "Of Sun Rays")
            .add(Elements.Dark, "Of Shadows")
            .tier(1, x -> Arrays.asList(new StatModifier(15, 20, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(11, 15, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(7, 11, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(4, x -> Arrays.asList(new StatModifier(5, 7, new ElementalDamageBonus(x), ModType.FLAT)))
            .tier(5, x -> Arrays.asList(new StatModifier(2, 5, new ElementalDamageBonus(x), ModType.FLAT)))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_philosopher")
            .Named("Of the Philosopher")
            .coreStat(Intelligence.INSTANCE)
            .includesTags(SlotTag.jewelry_family, SlotTag.cloth)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_titan")
            .Named("Of the Titan")
            .coreStat(Strength.INSTANCE)
            .includesTags(SlotTag.jewelry_family, SlotTag.plate)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_wind")
            .Named("Of the Wind")
            .coreStat(Dexterity.INSTANCE)
            .includesTags(SlotTag.jewelry_family, SlotTag.leather)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_wise")
            .Named("Of the Wise")
            .coreStat(Wisdom.INSTANCE)
            .includesTags(SlotTag.jewelry_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_agile")
            .Named("Of the Agile")
            .coreStat(Agility.INSTANCE)
            .includesTags(SlotTag.jewelry_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_virile")
            .Named("Of the Virile")
            .coreStat(Vitality.INSTANCE)
            .includesTags(SlotTag.jewelry_family)
            .excludesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_sky")
            .Named("Of the Sky")
            .tier(1, new StatModifier(0.3F, 0.4F, AllAttributes.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.2F, 0.3F, AllAttributes.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.1F, 0.2F, AllAttributes.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Weight(50)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_the_troll")
            .Named("Of The Troll")
            .tier(1, new StatModifier(1, 2, HealthRegen.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.5F, 1, HealthRegen.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.5F, HealthRegen.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.plate)
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_spirit_markings")
            .Named("Of Spirit Markings")
            .tier(1, new StatModifier(1, 2, ManaRegen.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.5F, 1, ManaRegen.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.5F, ManaRegen.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family, SlotTag.cloth)
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_treasure")
            .Named("Of Treasure")
            .tier(1, new StatModifier(10, 15F, TreasureQuality.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(7, 10, TreasureQuality.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(5, 7, TreasureQuality.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_affluence")
            .Named("Of Affluence")
            .tier(1, new StatModifier(7, 10, TreasureQuantity.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(5, 7, TreasureQuantity.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(3, 5, TreasureQuantity.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

        AffixBuilder.Normal("jewel_accuracy_suffix")
            .Named("Of Accuracy")
            .tier(1, new StatModifier(15, 20, Accuracy.getInstance()))
            .tier(2, new StatModifier(12, 15, Accuracy.getInstance()))
            .tier(3, new StatModifier(10, 12, Accuracy.getInstance()))
            .tier(4, new StatModifier(6, 10, Accuracy.getInstance()))
            .includesTags(SlotTag.jewelry_family)
            .Suffix()
            .Build();

    }
}
