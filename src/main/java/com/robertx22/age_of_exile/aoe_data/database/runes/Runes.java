package com.robertx22.age_of_exile.aoe_data.database.runes;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.util.registry.Registry;

public class Runes implements ExileRegistryInit {
    @Override
    public void registerAll() {
        RuneItems.ALL.forEach(obj -> {
            RuneItem x = obj.get();

            Rune rune = new Rune();
            rune.item_id = Registry.ITEM.getKey(x)
                .toString();
            rune.identifier = x.type.id;

            rune.tier = x.type.tier;

            rune.weight = x.type.weight;

            rune.addToSerializables();
        });
    }
}
