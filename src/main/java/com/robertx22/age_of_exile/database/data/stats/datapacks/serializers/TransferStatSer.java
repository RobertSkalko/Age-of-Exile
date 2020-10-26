package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ConvertFromOneToOtherStat;

public class TransferStatSer implements IStatSerializer<ConvertFromOneToOtherStat> {

    @Override
    public JsonObject statToJson(ConvertFromOneToOtherStat obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("adder_stat", obj.adder_stat);
        json.addProperty("add_to", obj.stat_to_add_to);
        json.addProperty("id", obj.GUID());
        return json;
    }

    @Override
    public ConvertFromOneToOtherStat getStatFromJson(JsonObject json) {
        String adder = json.get("adder_stat")
            .getAsString();
        String addto = json.get("add_to")
            .getAsString();
        ConvertFromOneToOtherStat stat = new ConvertFromOneToOtherStat(adder, addto);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}

