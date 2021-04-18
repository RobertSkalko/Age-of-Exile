package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class AttributeStatSer implements IStatSerializer<AttributeStat> {

    @Override
    public JsonObject statToJson(AttributeStat obj) {
        JsonObject json = new JsonObject();
        json.addProperty("attribute_id", obj.attributeId);
        json.addProperty("uuid", obj.uuid.toString());
        this.saveBaseStatValues(obj, json);
        return json;
    }

    @Override
    public AttributeStat getStatFromJson(JsonObject json) {

        Identifier ide = new Identifier(json.get("attribute_id")
            .getAsString());

        EntityAttribute attri = Registry.ATTRIBUTE.get(ide);

        AttributeStat stat = new AttributeStat("", "", UUID.fromString(json.get("uuid")
            .getAsString()), attri, false); // percent and id is loaded by basevalues

        this.loadBaseStatValues(stat, json);
        return stat;
    }
}