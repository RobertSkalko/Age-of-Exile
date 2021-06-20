package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.aoe_data.database.affixes.GenericAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusRequirement;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusYield;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;

import java.util.Arrays;

public class HoeRodAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new GenericAffixBuilder<BonusRequirement>().guid(x -> x.id + "_yield")
            .add(BonusRequirement.COLD_BIOME, "Cold Harvest")
            .add(BonusRequirement.HOT_BIOME, "Hot Harvest")
            .add(BonusRequirement.RAINING, "Rainy Harvest")
            .add(BonusRequirement.DAY, "Daily Harvest")
            .add(BonusRequirement.NIGHT, "Nightly Harvest")
            .tier(1, x -> Arrays.asList(new StatModifier(30, 30, new BonusYield(x))))
            .tier(2, x -> Arrays.asList(new StatModifier(20, 20, new BonusYield(x))))
            .tier(3, x -> Arrays.asList(new StatModifier(10, 10, new BonusYield(x))))
            .includesTags(SlotTag.hoe, SlotTag.fishing_rod)
            .Prefix()
            .Build();

    }
}
