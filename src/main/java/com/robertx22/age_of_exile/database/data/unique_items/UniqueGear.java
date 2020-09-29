package com.robertx22.age_of_exile.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IBaseGearType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class UniqueGear implements IByteBuf<UniqueGear>, IBaseGearType, ITiered, IAutoLocName, IAutoLocDesc,
    ISerializedRegistryEntry<UniqueGear>, ISerializable<UniqueGear> {

    public static UniqueGear SERIALIZER = new UniqueGear();

    public List<StatModifier> uniqueStats = new ArrayList<>();
    public int tier;
    public int weight = 1000;
    public String guid;
    public String gearType;
    public Identifier itemID;

    @Override
    public UniqueGear getFromBuf(PacketByteBuf buf) {
        UniqueGear uniq = new UniqueGear();

        int amount = buf.readInt();
        for (int i = 0; i < amount; i++) {
            uniq.uniqueStats.add(StatModifier.EMPTY.getFromBuf(buf));
        }
        uniq.guid = buf.readString(500);
        uniq.gearType = buf.readString(500);
        uniq.itemID = buf.readIdentifier();

        return uniq;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeInt(this.uniqueStats.size());
        uniqueStats.forEach(x -> x.toBuf(buf));
        buf.writeString(guid, 500);
        buf.writeString(gearType, 500);
        buf.writeIdentifier(itemID);
    }

    public transient String langName;
    public transient String langDesc;
    public transient BaseGearType serBaseGearType;

    @Override
    public String datapackFolder() {
        return getBaseGearType().family()
            .name()
            .toLowerCase(Locale.ROOT) + "/";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(uniqueStats(), json, "unique_stats");

        json.addProperty("gear_type", this.getBaseGearType()
            .GUID());
        json.addProperty("item_id", this.getResourceLocForItem()
            .toString());

        return json;
    }

    @Override
    public UniqueGear fromJson(JsonObject json) {

        UniqueGear uniq = new UniqueGear();

        uniq.guid = getGUIDFromJson(json);
        uniq.tier = getTierFromJson(json);
        uniq.weight = getWeightFromJson(json);

        uniq.itemID = new Identifier(json.get("item_id")
            .getAsString());

        uniq.uniqueStats = JsonUtils.getStats(json, "unique_stats");

        uniq.gearType = json.get("gear_type")
            .getAsString();

        return uniq;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public Rarity getRarity() {
        return SlashRegistry.GearRarities()
            .lowest();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Unique_Items;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.UNIQUE_GEAR;
    }

    public Identifier getResourceLocForItem() {
        return itemID;
    }

    public Item getUniqueItem() {
        return Registry.ITEM.get(itemID);
    }

    @Override
    public int getTier() {
        return tier;
    }

    public List<StatModifier> uniqueStats() {
        return this.uniqueStats;
    }

    @Override
    public String locDescForLangFile() {
        return this.langDesc;
    }

    @Override
    public String locNameForLangFile() {
        return this.langName;
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".unique_gear." + this.GUID() + ".desc";
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".unique_gear." + this.GUID() + ".name";
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public BaseGearType getBaseGearType() {
        if (!SlashRegistry.GearTypes()
            .isRegistered(this.gearType)) {
            assert this.serBaseGearType != null;
            return serBaseGearType;
        }
        return SlashRegistry.GearTypes()
            .get(gearType);
    }

}