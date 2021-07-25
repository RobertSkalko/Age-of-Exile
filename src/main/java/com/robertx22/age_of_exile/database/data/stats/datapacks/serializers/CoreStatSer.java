package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.AutoStatGson;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;

public class CoreStatSer implements IStatSerializer<CoreStat> {

    @Override
    public JsonObject statToJson(CoreStat obj) {
        JsonObject data = AutoStatGson.PARSER.parse(AutoStatGson.GSON.toJson(obj.data))
            .getAsJsonObject();

        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.add("core_stat_data", data);

        return json;
    }

    @Override
    public CoreStat getStatFromJson(JsonObject json) {
        CoreStatData data = AutoStatGson.GSON.fromJson(json.get("core_stat_data"), CoreStatData.class);
        String id = json.get("id")
            .getAsString();
        CoreStat stat = new CoreStat(id, id, data);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
