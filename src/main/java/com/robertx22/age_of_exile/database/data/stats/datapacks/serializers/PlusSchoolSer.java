package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.PlusSchoolLevels;

public class PlusSchoolSer implements IStatSerializer<PlusSchoolLevels> {

    @Override
    public JsonObject statToJson(PlusSchoolLevels obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("school", obj.school);
        json.addProperty("id", obj.GUID());
        return json;
    }

    @Override
    public PlusSchoolLevels getStatFromJson(JsonObject json) {
        String school = json.get("school")
            .getAsString();
        PlusSchoolLevels stat = new PlusSchoolLevels(school);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
