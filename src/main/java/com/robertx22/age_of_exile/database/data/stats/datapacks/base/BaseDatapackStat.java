package com.robertx22.age_of_exile.database.data.stats.datapacks.base;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.serializers.StatSerializers;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public abstract class BaseDatapackStat extends Stat implements ISerializable<Stat> {

    public static BaseDatapackStat MAIN_SERIALIZER = new BaseDatapackStat("") {
        @Override
        public String locDescForLangFile() {
            return "";
        }

        @Override
        public String locNameForLangFile() {
            return "";
        }

    };

    public Elements element = Elements.Physical;
    public String id = "";

    public BaseDatapackStat(String serializer) {
        this.serializer = serializer;
    }

    protected String serializer;

    @Override
    public boolean IsPercent() {
        return this.is_perc;
    }

    @Override
    public final Elements getElement() {
        return element;
    }

    @Override
    public final boolean isFromDatapack() {
        return true;
    }

    @Override
    public final String GUID() {
        return id;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("ser", serializer);

        Preconditions.checkArgument(StatSerializers.INSTANCE.map.containsKey(serializer));

        IStatSerializer seri = StatSerializers.INSTANCE.map.get(serializer);

        JsonElement serjson = seri
            .statToJson(this);

        json.add("data", serjson);
        return json;
    }

    @Override
    public Stat fromJson(JsonObject json) {
        this.serializer = json.get("ser")
            .getAsString();

        if (serializer.equals(DatapackStat.SER)) {
            return DatapackStat.SERIALIZER.fromJson(json);
        }

        Preconditions.checkArgument(StatSerializers.INSTANCE.map.containsKey(serializer));

        BaseDatapackStat stat = StatSerializers.INSTANCE.map.get(serializer)
            .getStatFromJson(json.get("data")
                .getAsJsonObject());

        return stat;
    }

}
