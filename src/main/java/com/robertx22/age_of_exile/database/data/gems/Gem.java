package com.robertx22.age_of_exile.database.data.gems;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Gem extends BaseRuneGem implements IAutoGson<Gem>, JsonExileRegistry<Gem> {

    public static Gem SERIALIZER = new Gem();

    public String socket_type = "";
    public String text_format = "";

    public Formatting getFormat() {
        try {
            return Formatting.valueOf(text_format);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Formatting.GRAY;
    }

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    @Override
    public Class<Gem> getClassForSerialization() {
        return Gem.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEM;
    }

}
