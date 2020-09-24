package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.GiveSpellStat;

public class SpellStatSer implements IStatSerializer<GiveSpellStat> {

    @Override
    public JsonObject statToJson(GiveSpellStat obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("spell", obj.spell);
        return json;
    }

    @Override
    public GiveSpellStat getStatFromJson(JsonObject json) {
        String spell = json.get("spell")
            .getAsString();
        GiveSpellStat stat = new GiveSpellStat(spell);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
