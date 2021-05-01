package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

import java.util.HashMap;

public abstract class StatEffect<T> implements ISerializedRegistryEntry<StatEffect>, IAutoGson<StatEffect<T>> {

    public static StatEffect SERIALIZER = new SetBooleanEffect();
    public static HashMap<String, StatEffect> SERIALIZERS = new HashMap<>();

    static {
        addSer(new SetBooleanEffect());
        addSer(new IncreaseNumberEffect());
        addSer(new GiveExileStatusEffect());
    }

    static void addSer(StatEffect eff) {
        SERIALIZERS.put(eff.ser, eff);
    }

    public String id = "";
    public String ser = "";

    public StatEffect(String id, String ser) {
        this.id = id;
        this.ser = ser;
    }

    public abstract void activate(T event, StatData data, Stat stat);

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.STAT_EFFECT;
    }

    @Override
    public String GUID() {
        return id;
    }

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

