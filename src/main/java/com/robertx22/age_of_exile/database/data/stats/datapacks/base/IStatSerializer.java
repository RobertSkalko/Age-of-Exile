package com.robertx22.age_of_exile.database.data.stats.datapacks.base;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public interface IStatSerializer<T extends DatapackStat> {

    JsonObject statToJson(T obj);

    T getStatFromJson(JsonObject json);

    default void saveBaseStatValues(T obj, JsonObject json) {

        json.addProperty("min", obj.min_val);
        json.addProperty("max", obj.max_val);
        json.addProperty("base", obj.base_val);
        json.addProperty("perc", obj.is_percent);
        json.addProperty("sec_val", obj.uses_second_val);
        json.addProperty("ele", obj.element != null ? obj.element.name() : Elements.Physical.name());
        json.addProperty("scale", obj.scaling.name());
    }

    default void loadBaseStatValues(T obj, JsonObject json) {

        obj.min_val = json.get("min")
            .getAsFloat();
        obj.max_val = json.get("max")
            .getAsFloat();
        obj.base_val = json.get("base")
            .getAsFloat();
        obj.is_percent = json.get("perc")
            .getAsBoolean();
        obj.uses_second_val = json.get("sec_val")
            .getAsBoolean();
        obj.element = Elements.valueOf(json.get("ele")
            .getAsString());
        obj.scaling = StatScaling.valueOf(json.get("scale")
            .getAsString());

    }

}
