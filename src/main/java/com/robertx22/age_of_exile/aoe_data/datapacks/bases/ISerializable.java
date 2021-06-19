package com.robertx22.age_of_exile.aoe_data.datapacks.bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

public interface ISerializable<T> {
    JsonObject toJson();

    T fromJson(JsonObject json);

    static String ID = "id";
    static String WEIGHT = "weight";
    static String RARITY = "rarity";

    default String datapackFolder() {
        return "";
    }

    default String getGUIDFromJson(JsonObject json) {
        return json.get(ID)
            .getAsString();
    }

    default int getWeightFromJson(JsonObject json) {
        return json.get(WEIGHT)
            .getAsInt();
    }

    default int getRarityFromJson(JsonObject json) {
        return json.get(RARITY)
            .getAsInt();
    }

    default JsonObject getDefaultJson() {
        JsonObject json = new JsonObject();

        if (this instanceof IGUID) {
            IGUID claz = (IGUID) this;
            json.addProperty(ID, claz.GUID());
        }

        if (this instanceof IWeighted) {
            IWeighted claz = (IWeighted) this;
            json.addProperty(WEIGHT, claz.Weight());
        }

        if (this instanceof IRarity) {
            IRarity claz = (IRarity) this;
            json.addProperty(RARITY, claz.getRarityRank());
        }

        return json;
    }

    default String toJsonString() {
        return toJson().toString();
    }

    default boolean shouldGenerateJson() {
        return true;
    }

}
