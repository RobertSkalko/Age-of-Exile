package com.robertx22.age_of_exile.database.data.unique_items;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFiltersGroupData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
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
    public List<StatModifier> base_stats = new ArrayList<>();
    public StatRequirement stat_req = new StatRequirement();
    public int tier;
    public int weight = 1000;
    public String guid;
    public String gearType;
    public Identifier itemID;
    public String uniqueRarity = IRarity.UNIQUE_ID;
    public String set = "";
    public boolean replaces_name = false;

    public List<String> gear_types = new ArrayList<>();

    public DropFiltersGroupData filters = new DropFiltersGroupData();

    public transient String langName;
    public transient String langDesc;
    public transient BaseGearType serBaseGearType;

    public boolean hasSet() {
        return Database.Sets()
            .isRegistered(set);
    }

    public GearSet getSet() {
        return Database.Sets()
            .get(set);
    }

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
        JsonUtils.addStats(base_stats, json, "base_stats");

        json.addProperty("gear_type", this.getBaseGearType()
            .GUID());
        json.addProperty("item_id", this.getResourceLocForItem()
            .toString());

        json.addProperty("rarity", this.uniqueRarity);
        json.addProperty("set", this.set);
        json.addProperty("replaces_name", this.replaces_name);

        json.add("gear_types", JsonUtils.stringListToJsonArray(gear_types));

        json.add("stat_req", stat_req.toJson());
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
        uniq.base_stats = JsonUtils.getStats(json, "base_stats");

        uniq.gear_types = JsonUtils.jsonArrayToStringList(json.get("gear_types")
            .getAsJsonArray());

        uniq.gearType = json.get("gear_type")
            .getAsString();
        uniq.uniqueRarity = json.get("rarity")
            .getAsString();
        if (json.has("set")) {
            uniq.set = json.get("set")
                .getAsString();
        }
        if (json.has("replaces_name")) {
            uniq.replaces_name = json.get("replaces_name")
                .getAsBoolean();
        }
        uniq.filters = DropFiltersGroupData.fromJson(json.get("filters"));

        try {
            uniq.stat_req = StatRequirement.EMPTY.fromJson(json.get("stat_req")
                .getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public GearRarity getUniqueRarity() {
        return Database.GearRarities()
            .get(uniqueRarity);
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