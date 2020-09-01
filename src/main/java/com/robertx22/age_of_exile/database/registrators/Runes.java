package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.util.registry.Registry;

public class Runes implements ISlashRegistryInit {
    @Override
    public void registerAll() {
        ModRegistry.RUNES.ALL.forEach(x -> {
            Rune rune = new Rune();
            rune.item_id = Registry.ITEM.getId(x)
                .toString();
            rune.identifier = x.type.id;

            rune.on_armor_stats = x.getStatsForSerialization(BaseGearType.SlotFamily.Armor);
            rune.on_weapons_stats = x.getStatsForSerialization(BaseGearType.SlotFamily.Weapon);
            rune.on_jewelry_stats = x.getStatsForSerialization(BaseGearType.SlotFamily.Jewelry);

            rune.effective_level = x.type.lvl;
            rune.required_item_level = x.type.lvl;

            rune.weight = x.type.weight;

            rune.addToSerializables();
        });
    }
}
