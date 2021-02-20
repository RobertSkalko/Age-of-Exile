package com.robertx22.age_of_exile.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFiltersGroupData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IBaseGearType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UniqueGear implements IBaseGearType, ITiered, IAutoLocName, IAutoLocDesc,
    ISerializedRegistryEntry<UniqueGear>, ISerializable<UniqueGear> {

    public static UniqueGear SERIALIZER = new UniqueGear();

    public List<StatModifier> uniqueStats = new ArrayList<>();
    public int tier;
    public int weight = 1000;
    public String guid;
    public String gearType;
    public Identifier itemID;
    public String uniqueRarity = IRarity.UNIQUE_ID;

    public List<String> gear_types = new ArrayList<>();

    public DropFiltersGroupData filters = new DropFiltersGroupData();

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

        json.addProperty("rarity", this.uniqueRarity);

        json.add("filters", filters.toJson());
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
        uniq.uniqueRarity = json.get("rarity")
            .getAsString();

        uniq.filters = DropFiltersGroupData.fromJson(json.get("filters"));

        return uniq;
    }

    @Override
    public int Weight() {
        return weight;
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

    public List<BaseGearType> getPossibleGearTypes() {

        return gear_types.stream()
            .map(x -> Database.GearTypes()
                .get(x))
            .collect(Collectors.toList());
    }

    @Override
    public BaseGearType getBaseGearType() {
        if (!Database.GearTypes()
            .isRegistered(this.gearType)) {
            assert this.serBaseGearType != null;
            return serBaseGearType;
        }
        return Database.GearTypes()
            .get(gearType);
    }

}