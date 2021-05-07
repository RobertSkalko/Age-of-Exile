package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class NewPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.bigStat("big_hp_res", "Hard Life",
            new OptScaleExactStat(15, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT)
        );

    }
}
