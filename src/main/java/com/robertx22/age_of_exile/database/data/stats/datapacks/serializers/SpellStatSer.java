package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base.DatapackSpellStat;

import java.util.function.Function;

public class SpellStatSer<T extends DatapackSpellStat> implements IStatSerializer<T> {
    Function<String, T> newintance;

    public SpellStatSer(Function<String, T> newintance) {
        this.newintance = newintance;
    }

    @Override
    public JsonObject statToJson(T obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("spell", obj.spell);
        return json;
    }

    @Override
    public T getStatFromJson(JsonObject json) {
        String spell = json.get("spell")
            .getAsString();
        T stat = this.newintance.apply(spell);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
