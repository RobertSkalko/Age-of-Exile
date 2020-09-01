package com.robertx22.age_of_exile.database.data.gems;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.age_of_exile.config.forge.ModConfig.get;

public class Gem implements IAutoGson<Gem>, ISerializedRegistryEntry<Gem> {

    public static Gem SERIALIZER = new Gem();

    public List<StatModifier> on_armor_stats = new ArrayList<>();

    public List<StatModifier> on_jewelry_stats = new ArrayList<>();

    public List<StatModifier> on_weapons_stats = new ArrayList<>();

    public String item_id = "";

    public String identifier = "";

    public float effective_level;

    public float required_item_level;

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    public int getReqLevel() {
        return (int) (MathHelper.clamp(get().Server.MAX_LEVEL * required_item_level, 1, get().Server.MAX_LEVEL));
    }

    public int getEffectiveLevel() {
        return (int) (MathHelper.clamp(get().Server.MAX_LEVEL * effective_level, 1, get().Server.MAX_LEVEL));
    }

    public final List<StatModifier> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return on_armor_stats;
        }
        if (sfor == SlotFamily.Jewelry) {
            return on_jewelry_stats;
        }
        if (sfor == SlotFamily.Weapon) {
            return on_weapons_stats;
        }
        return null;

    }

    @Override
    public Class<Gem> getClassForSerialization() {
        return Gem.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEM;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}
