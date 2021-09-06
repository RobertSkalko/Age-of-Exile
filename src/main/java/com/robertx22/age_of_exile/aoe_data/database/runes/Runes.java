package com.robertx22.age_of_exile.aoe_data.database.runes;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.util.registry.Registry;

public class Runes implements ExileRegistryInit {
    @Override
    public void registerAll() {
        ModRegistry.RUNES.ALL.forEach(x -> {
            Rune rune = new Rune();
            rune.item_id = Registry.ITEM.getId(x)
                .toString();
            rune.identifier = x.type.id;

            rune.tier = x.type.tier;

            rune.weight = x.type.weight;

            rune.addToSerializables();
        });
    }
}
