package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

import java.util.HashMap;

public abstract class StatEffect<T> implements IAutoGson<StatEffect<T>> {

    public String ser = "";

    public static HashMap<String, StatEffect> SERIALIZERS = new HashMap<>();

    public StatEffect(String ser) {
        this.ser = ser;
    }

    public abstract void activate(T event, StatData data, Stat stat);

    @Override
    public final StatEffect fromJson(JsonObject json) {
        String ser = json.get("ser")
            .getAsString();

        StatEffect<T> t = (StatEffect<T>) GSON.fromJson(json, SERIALIZERS.get(ser)
            .getSerClass());
        t.onLoadedFromJson();
        return t;
    }

    @Override
    public Class getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatEffect> getSerClass();
}

