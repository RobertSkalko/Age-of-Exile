package com.robertx22.age_of_exile.database.data.gems;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Gem extends BaseRuneGem implements IAutoGson<Gem>, ISerializedRegistryEntry<Gem> {

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

    public String getIcon() {
        if (this.effective_level <= 0.2F) {
            return "\u2736";
        }
        if (this.effective_level <= 0.4F) {
            return "\u2737";
        }
        if (this.effective_level <= 0.6F) {
            return "\u2738";
        }
        if (this.effective_level <= 0.8F) {
            return "\u2739";
        }

        return "\u273A";
    }

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    @Override
    public Class<Gem> getClassForSerialization() {
        return Gem.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEM;
    }

}
