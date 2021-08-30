package com.robertx22.age_of_exile.database.data.gear_slots;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public class GearSlot implements JsonExileRegistry<GearSlot>, ISerializable<GearSlot>, IAutoLocName {

    public static GearSlot SERIALIZER = new GearSlot("", "", 0);

    public String guid;
    public int weight;
    public transient String locname = "";

    public GearSlot(String guid, String name, int weight) {
        this.guid = guid;
        this.locname = name;
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
        json.addProperty("weight", weight);

        return json;
    }

    @Override
    public GearSlot fromJson(JsonObject json) {
        return new GearSlot(json.get("id")
            .getAsString(), "", json.get("weight")
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
