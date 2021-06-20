package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class NonWeaponSuffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_res")
            .add(Elements.Fire, "Of the Drake")
            .add(Elements.Water, "Of the Yeti")
            .add(Elements.Nature, "Of the Snake")
            .add(Elements.Light, "Of the Gryphon")
            .add(Elements.Dark, "Of the Leech")

            .tier(1, x -> Arrays.asList(new StatModifier(35, 40, new ElementalResist(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(30, 35, new ElementalResist(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(25, 30, new ElementalResist(x), ModType.FLAT)))
            .tier(4, x -> Arrays.asList(new StatModifier(20, 25, new ElementalResist(x), ModType.FLAT)))
            .tier(5, x -> Arrays.asList(new StatModifier(10, 15, new ElementalResist(x), ModType.FLAT)))
            .includesTags(SlotTag.jewelry_family, SlotTag.armor_family, SlotTag.offhand_family)
            .Weight(4000)
            .Suffix()
            .Build();

    }

}


