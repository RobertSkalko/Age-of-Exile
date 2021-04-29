package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

import java.util.HashMap;

public abstract class StatCondition<T> implements IAutoGson<StatCondition<T>> {

    public String ser = "";

    public static HashMap<String, StatCondition> SERIALIZERS = new HashMap<>();

    public StatCondition(String ser) {
        this.ser = ser;
    }

    public abstract boolean can(T event, StatData data, Stat stat);

    @Override
    public final StatCondition fromJson(JsonObject json) {
        String ser = json.get("ser")
            .getAsString();

        StatCondition<T> t = (StatCondition<T>) GSON.fromJson(json, SERIALIZERS.get(ser)
            .getSerClass());
        t.onLoadedFromJson();
        return t;
    }

    @Override
    public Class getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatCondition> getSerClass();
}
