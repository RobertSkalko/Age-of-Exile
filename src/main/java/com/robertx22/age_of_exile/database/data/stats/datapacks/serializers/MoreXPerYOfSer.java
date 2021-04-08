package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MoreXPerYOf;

public class MoreXPerYOfSer implements IStatSerializer<MoreXPerYOf> {

    @Override
    public JsonObject statToJson(MoreXPerYOf obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("adder_stat", obj.adder_stat);
        json.addProperty("add_to", obj.stat_to_add_to);
        json.addProperty("id", obj.GUID());
        json.addProperty("per_amount", obj.perEach);
        return json;
    }

    @Override
    public MoreXPerYOf getStatFromJson(JsonObject json) {
        String adder = json.get("adder_stat")
            .getAsString();
        String addto = json.get("add_to")
            .getAsString();
        MoreXPerYOf stat = new MoreXPerYOf(adder, addto);
        stat.perEach = json.get("per_amount")
            .getAsInt();
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
