package com.robertx22.mine_and_slash.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.data_generation.JsonUtils;
import com.robertx22.mine_and_slash.data_generation.unique_gears.SerializableUniqueGear;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IBaseGearType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ITiered;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Locale;

public interface IUnique extends IBaseGearType, ITiered, IAutoLocName, IAutoLocDesc,
    ISerializedRegistryEntry<IUnique>, ISerializable<IUnique> {

    @Override
    default boolean isFromDatapack() {
        return true;
    }

    @Override
    default String datapackFolder() {
        return getBaseGearType().GUID() + "/";
    }

    @Override
    default JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(uniqueStats(), json, "unique_stats");

        json.addProperty("gear_type", this.getBaseGearType()
            .GUID());
        json.addProperty("item_id", this.getResourceLocForItem()
            .toString());

        return json;
    }

    @Override
    default IUnique fromJson(JsonObject json) {

        String guid = getGUIDFromJson(json);
        String name = getLangNameStringFromJson(json);
        String desc = getLangDescStringFromJson(json);
        int tier = getTierFromJson(json);
        int weight = getWeightFromJson(json);

        Identifier loc = new Identifier(json.get("item_id")
            .getAsString());

        List<StatModifier> unique = JsonUtils.getStats(json, "unique_stats");

        String slot = json.get("gear_type")
            .getAsString();

        return new SerializableUniqueGear(unique, tier, weight, guid, name, desc, slot, loc);

    }

    @Override
    public default int Weight() {
        return this.getRarity()
            .Weight();
    }

    List<StatModifier> uniqueStats();

    @Override
    public default int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public default Rarity getRarity() {
        return Rarities.Gears.get(0);
    }

    default String getGeneratedResourceID() {
        return getGeneratedResourceFolderPath() + GUID();
    }

    default String getGeneratedResourceFolderPath() {
        return "uniques/" + getBaseGearType().family()
            .name()
            .toLowerCase(Locale.ROOT) + "/";
    }

    @Override
    public default AutoLocGroup locNameGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public default AutoLocGroup locDescGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public default SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.UNIQUE_GEAR;
    }

    default Identifier getResourceLocForItem() {
        return new Identifier(Ref.MODID, getGeneratedResourceID());
    }

    default Item getUniqueItem() {
        return ForgeRegistries.ITEMS.getValue(getResourceLocForItem());
    }

    @Override
    default String locDescLangFileGUID() {
        return "item." + getUniqueItem().getRegistryName()
            .toString()
            + ".desc";
    }

    @Override
    public default String getPrefix() {
        return "";
    }

    @Override
    default String locNameLangFileGUID() {
        return "item." + getUniqueItem().getRegistryName()
            .toString();
    }

}