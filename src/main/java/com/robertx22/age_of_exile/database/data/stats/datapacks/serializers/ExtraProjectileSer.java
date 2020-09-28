package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ExtraSpellProjectilesStat;

public class ExtraProjectileSer implements IStatSerializer<ExtraSpellProjectilesStat> {

    @Override
    public JsonObject statToJson(ExtraSpellProjectilesStat obj) {
        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.addProperty("spell", obj.spell);
        return json;
    }

    @Override
    public ExtraSpellProjectilesStat getStatFromJson(JsonObject json) {
        String spell = json.get("spell")
            .getAsString();
        ExtraSpellProjectilesStat stat = new ExtraSpellProjectilesStat(spell);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
