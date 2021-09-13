package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.database.stats.PlusSpellLevelStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;

public class PlusLevelSpellSer implements IStatSerializer<PlusSpellLevelStat> {

    @Override
    public JsonObject statToJson(PlusSpellLevelStat obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("spell", obj.spell);
        json.addProperty("id", obj.GUID());
        return json;
    }

    @Override
    public PlusSpellLevelStat getStatFromJson(JsonObject json) {
        String school = json.get("spell")
            .getAsString();
        PlusSpellLevelStat stat = new PlusSpellLevelStat(school);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
