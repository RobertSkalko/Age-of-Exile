package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ExileEffects implements ISlashRegistryInit {

    public static Integer ELE_WEAKNESS = 0;

    @Override
    public void registerAll() {
        ExileEffectBuilder.of(ELE_WEAKNESS, "Ele Weakness")
            .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .build();

    }
}
