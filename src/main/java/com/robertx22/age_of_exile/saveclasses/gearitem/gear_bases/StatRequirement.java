package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

public class StatRequirement implements ISerializable<StatRequirement> {

    public static StatRequirement EMPTY = new StatRequirement();

    public int base_dex = 0;
    public int base_int = 0;
    public int base_str = 0;

    public float dex_req = 0;
    public float int_req = 0;
    public float str_req = 0;

    public StatRequirement() {
    }

    private int getDex(GearItemData gear) {
        return base_dex + (int) scale(dex_req, gear);
    }

    private int getInt(GearItemData gear) {
        return base_int + (int) scale(int_req, gear);
    }

    private int getStr(GearItemData gear) {
        return base_str + (int) scale(str_req, gear);
    }

    private int getDex(int lvl) {
        return base_dex + (int) Dexterity.INSTANCE.scale(dex_req, lvl);
    }

    private int getInt(int lvl) {
        return base_int + (int) Dexterity.INSTANCE.scale(int_req, lvl);
    }

    private int getStr(int lvl) {
        return base_str + (int) Dexterity.INSTANCE.scale(str_req, lvl);
    }

    private float scale(float val, GearItemData gear) {
        if (val <= 0) {
            return 0;
        }

        float calc = (float) (val * gear.getRarity()
            .statReqMulti());

        return (int) Dexterity.INSTANCE.scale(calc, gear.level);
    }

    public StatRequirement setDex(float dex_req) {
        this.dex_req = dex_req;
        return this;
    }

    public StatRequirement setInt(float int_req) {
        this.int_req = int_req;
        return this;
    }

    public StatRequirement setStr(float str_req) {
        this.str_req = str_req;
        return this;
    }

    public StatRequirement setBaseDex(int dex_req) {
        this.base_dex = dex_req;
        return this;
    }

    public StatRequirement setBaseInt(int int_req) {
        this.base_int = int_req;
        return this;
    }

    public StatRequirement setBaseStr(int str_req) {
        this.base_str = str_req;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        if (dex_req > 0) {
            json.addProperty("dex_req", dex_req);
        }
        if (int_req > 0) {
            json.addProperty("int_req", int_req);
        }
        if (str_req > 0) {
            json.addProperty("str_req", str_req);
        }
        if (base_dex > 0) {
            json.addProperty("base_dex", base_dex);
        }
        if (base_int > 0) {
            json.addProperty("base_int", base_int);
        }
        if (base_str > 0) {
            json.addProperty("base_str", base_str);
        }

        return json;

    }

    @Override
    public StatRequirement fromJson(JsonObject json) {
        StatRequirement r = new StatRequirement();

        if (json.has("dex_req")) {
            r.dex_req = json.get("dex_req")
                .getAsFloat();
        }
        if (json.has("int_req")) {
            r.int_req = json.get("int_req")
                .getAsFloat();
        }
        if (json.has("str_req")) {
            r.str_req = json.get("str_req")
                .getAsFloat();
        }

        if (json.has("base_str")) {
            r.base_str = json.get("base_str")
                .getAsInt();
        }
        if (json.has("base_int")) {
            r.base_int = json.get("base_int")
                .getAsInt();
        }
        if (json.has("base_dex")) {
            r.base_dex = json.get("base_dex")
                .getAsInt();
        }
        return r;

    }
}
