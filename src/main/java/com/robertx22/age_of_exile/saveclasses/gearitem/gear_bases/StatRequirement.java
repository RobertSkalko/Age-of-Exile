package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class StatRequirement implements ISerializable<StatRequirement> {

    public static String PLUS_ICON = "\u271A";

    //public static String CHECK_YES_ICON = "\u2713";
    // public static String NO_ICON = "\u2715";

    public static StatRequirement EMPTY = new StatRequirement();

    public int base_dex = 0;
    public int base_int = 0;
    public int base_str = 0;

    public float dex_req = 0;
    public float int_req = 0;
    public float str_req = 0;

    public StatRequirement(StatRequirement r) {
        this.base_int = r.base_int;
        this.base_dex = r.base_dex;
        this.base_str = r.base_str;

        this.dex_req = r.dex_req;
        this.int_req = r.int_req;
        this.str_req = r.str_req;

    }

    public StatRequirement() {
    }

    public boolean meetsReq(int lvl, EntityCap.UnitData data) {

        if (data.getUnit()
            .getCalculatedStat(Strength.INSTANCE)
            .getAverageValue() < getStr(lvl)) {
            return false;
        }

        float intv = data.getUnit()
            .getCalculatedStat(Intelligence.INSTANCE)
            .getAverageValue();
        float intreq = getInt(lvl);
        if (intv < intreq) {
            return false;
        }
        if (data.getUnit()
            .getCalculatedStat(Dexterity.INSTANCE)
            .getAverageValue() < getDex(lvl)) {
            return false;
        }

        return true;

    }

    private int getDex(int lvl) {
        return base_dex + (int) StatScaling.STAT_REQ.scale(dex_req, lvl);
    }

    private int getInt(int lvl) {
        return base_int + (int) StatScaling.STAT_REQ.scale(int_req, lvl);
    }

    private int getStr(int lvl) {
        return base_str + (int) StatScaling.STAT_REQ.scale(str_req, lvl);
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

    public List<Text> GetTooltipString(int lvl, EntityCap.UnitData data) {
        List<Text> list = new ArrayList<>();

        int dex = getDex(lvl);
        int str = getStr(lvl);
        int inte = getInt(lvl);

        if (dex > 0) {
            list.add(getTooltip(dex, Dexterity.INSTANCE, data));
        }

        if (str > 0) {
            list.add(getTooltip(str, Strength.INSTANCE, data));

        }
        if (inte > 0) {
            list.add(getTooltip(inte, Intelligence.INSTANCE, data));
        }

        return list;
    }

    static Text getTooltip(int req, Stat stat, EntityCap.UnitData data) {

        if (data.getUnit()
            .getCalculatedStat(stat)
            .getAverageValue() >= req) {
            return new LiteralText(Formatting.GREEN + "" + PLUS_ICON + " ").append(stat.locName()
                .formatted(Formatting.GRAY))
                .append(" " + Formatting.GRAY + "Min: " + req + " ");
        } else {

            return new LiteralText(Formatting.RED + "" + PLUS_ICON + " ").append(stat.locName()
                .formatted(Formatting.GRAY))
                .append(" " + Formatting.GRAY + "Min: " + req + " ");

        }

    }
}
