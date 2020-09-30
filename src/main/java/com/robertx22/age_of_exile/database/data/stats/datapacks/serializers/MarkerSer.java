package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;

public class MarkerSer implements IStatSerializer<MarkerStat> {

    @Override
    public JsonObject statToJson(MarkerStat obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("id", obj.GUID());
        return json;
    }

    @Override
    public MarkerStat getStatFromJson(JsonObject json) {
        MarkerStat stat = new MarkerStat();
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
