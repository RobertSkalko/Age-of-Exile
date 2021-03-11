package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.aoe_data.database.affixes.GenericAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusRequirement;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusYield;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;

public class HoeRodAffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new GenericAffixBuilder<BonusRequirement>().guid(x -> x.id + "_yield")
            .add(BonusRequirement.COLD_BIOME, "Cold Biome")
            .add(BonusRequirement.HOT_BIOME, "Hot Biome")
            .add(BonusRequirement.RAINING, "Rainy")
            .add(BonusRequirement.DAY, "Daylight")
            .add(BonusRequirement.NIGHT, "Night")
            .tier(1, x -> Arrays.asList(new StatModifier(9, 12, new BonusYield(x))))
            .tier(2, x -> Arrays.asList(new StatModifier(7, 9, new BonusYield(x))))
            .tier(3, x -> Arrays.asList(new StatModifier(4, 7, new BonusYield(x))))
            .includesTags(SlotTag.hoe, SlotTag.fishing_rod)
            .Prefix()
            .Build();

    }
}
