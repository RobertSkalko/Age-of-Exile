package com.robertx22.age_of_exile.aoe_data.database.gems;

import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GemItems;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.util.registry.Registry;

public class Gems implements ExileRegistryInit {

    @Override
    public void registerAll() {
        GemItems.ALL.forEach(g -> {
            GemItem x = g.get();

            Gem gem = new Gem();
            gem.item_id = Registry.ITEM.getKey(x)
                .toString();
            gem.identifier = x.gemType.id + x.gemRank.num;

            gem.on_armor_stats = x.getStatsForSerialization(SlotFamily.Armor);
            gem.on_weapons_stats = x.getStatsForSerialization(SlotFamily.Weapon);
            gem.on_jewelry_stats = x.getStatsForSerialization(SlotFamily.Jewelry);

            gem.tier = x.gemRank.tier;
            gem.socket_type = x.gemType.id;
            gem.text_format = x.gemType.format.name();

            gem.weight = x.weight;

            gem.addToSerializables();
        });
    }
}
