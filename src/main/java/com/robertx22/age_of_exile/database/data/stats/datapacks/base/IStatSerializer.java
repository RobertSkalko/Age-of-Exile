package com.robertx22.age_of_exile.database.data.stats.datapacks.base;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public interface IStatSerializer<T extends BaseDatapackStat> {

    JsonObject statToJson(T obj);

    T getStatFromJson(JsonObject json);

    default void saveBaseStatValues(T obj, JsonObject json) {

        json.addProperty("id", obj.id);
        json.addProperty("min", obj.min);
        json.addProperty("max", obj.max);
        json.addProperty("base", obj.base);
        json.addProperty("perc", obj.is_perc);
        json.addProperty("ele", obj.element != null ? obj.element.name() : Elements.Physical.name());
        json.addProperty("scale", obj.scaling.name());
    }

    default void loadBaseStatValues(T obj, JsonObject json) {

        obj.id = json.get("id")
            .getAsString();
        obj.min = json.get("min")
            .getAsFloat();
        obj.max = json.get("max")
            .getAsFloat();
        obj.base = json.get("base")
            .getAsFloat();
        obj.is_perc = json.get("perc")
            .getAsBoolean();
        obj.element = Elements.valueOf(json.get("ele")
            .getAsString());
        obj.scaling = StatScaling.valueOf(json.get("scale")
            .getAsString());

    }

}
