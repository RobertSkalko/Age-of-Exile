package com.robertx22.age_of_exile.database.data.runes;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Rune extends BaseRuneGem implements IAutoGson<Rune>, ISerializedRegistryEntry<Rune> {

    public static Rune SERIALIZER = new Rune();

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    @Override
    public Class<Rune> getClassForSerialization() {
        return Rune.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RUNE;
    }

}
