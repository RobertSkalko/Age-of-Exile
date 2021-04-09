package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AddPerPercentOfOther;

public class OneAppliesToOtherSer implements IStatSerializer<AddPerPercentOfOther> {

    @Override
    public JsonObject statToJson(AddPerPercentOfOther obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("adder_stat", obj.adder_stat);
        json.addProperty("add_to", obj.stat_to_add_to);
        json.addProperty("id", obj.GUID());
        return json;
    }

    @Override
    public AddPerPercentOfOther getStatFromJson(JsonObject json) {
        String adder = json.get("adder_stat")
            .getAsString();
        String addto = json.get("add_to")
            .getAsString();
        AddPerPercentOfOther stat = new AddPerPercentOfOther(adder, addto);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
