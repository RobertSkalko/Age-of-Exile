package com.robertx22.age_of_exile.database.data.runes;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Rune extends BaseRuneGem implements IAutoGson<Rune>, JsonExileRegistry<Rune> {

    public static Rune SERIALIZER = new Rune();

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    @Override
    public Class<Rune> getClassForSerialization() {
        return Rune.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNE;
    }

}
