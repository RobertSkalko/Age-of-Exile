package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.LeechInfo;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class BigEleDotAndLeechPerks implements ExileRegistryInit {

    static void breath(String name, Elements ele) {

        PerkBuilder.bigStat("big_breath_of_" + ele.guidName, "Breath of " + name,
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(ele), ModType.FLAT),
            new OptScaleExactStat(15, Stats.ELE_DOT_DAMAGE.get(ele), ModType.FLAT)
        );
    }

    static void heart(String name, Elements ele) {
        PerkBuilder.bigStat("big_heart_of_" + ele.guidName, "Heart of " + name,
            new OptScaleExactStat(10, Stats.ELEMENTAL_SPELL_DAMAGE.get(ele), ModType.FLAT),
            new OptScaleExactStat(2, new ElementalPenetration(ele), ModType.FLAT),
            new OptScaleExactStat(2, Stats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(ele, ResourceType.health)), ModType.FLAT)
        );
    }

    static void dominator(String name, Elements ele) {
        PerkBuilder.bigStat(ele.guidName + "_dom", name + " Dominator",
            new OptScaleExactStat(5, new ElementalPenetration(ele), ModType.FLAT),
            new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(2, Stats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(ele, ResourceType.health)))
        );

    }

    @Override
    public void registerAll() {

        breath("Night", Elements.Dark);
        breath("Light", Elements.Light);
        breath("Fire", Elements.Fire);
        breath("Ice", Elements.Water);
        breath("Poison", Elements.Nature);

        heart("Night", Elements.Dark);
        heart("Light", Elements.Light);
        heart("Nature", Elements.Nature);
        heart("Fire", Elements.Fire);
        heart("Water", Elements.Water);

        dominator("Night", Elements.Dark);
        dominator("Light", Elements.Light);
        dominator("Poison", Elements.Nature);
        dominator("Fire", Elements.Fire);
        dominator("Ice", Elements.Water);

    }
}
