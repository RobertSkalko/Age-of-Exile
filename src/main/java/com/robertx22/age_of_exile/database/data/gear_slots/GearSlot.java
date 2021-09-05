package com.robertx22.age_of_exile.database.data.gear_slots;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public class GearSlot implements JsonExileRegistry<GearSlot>, ISerializable<GearSlot>, IAutoLocName {

    public static GearSlot SERIALIZER = new GearSlot("", "", 1, -1, 0);

    public String guid;
    public int weight;
    public int energy_cost;
    public int custom_model_data_num = -1;
    public transient String locname = "";

    public GearSlot(String guid, String name, int energy_cost, int modelnnum, int weight) {
        this.guid = guid;
        this.energy_cost = energy_cost;
        this.locname = name;
        this.custom_model_data_num = modelnnum;
        this.weight = weight;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_SLOT;
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", guid);
        json.addProperty("energy_cost", energy_cost);
        json.addProperty("weight", weight);
        json.addProperty("model_num", custom_model_data_num);

        return json;
    }

    @Override
    public GearSlot fromJson(JsonObject json) {
        return new GearSlot(json.get("id")
            .getAsString(), "", json.get("energy_cost")
            .getAsInt(), json.get("weight")
            .getAsInt(), json.get("model_num")
            .getAsInt());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Slots;
    }

    @Override
    public String locNameLangFileGUID() {
        return "mmorpg.gearslot." + guid;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
