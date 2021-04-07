package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatRequirement implements IAutoGson<StatRequirement> {

    public static String CHECK_YES_ICON = "\u2713";
    public static String NO_ICON = "\u2715";

    public static StatRequirement EMPTY = new StatRequirement();

    public HashMap<String, Float> base_req = new HashMap<>();
    public HashMap<String, Float> scaling_req = new HashMap<>();

    public StatRequirement(StatRequirement r) {

        this.base_req = new HashMap<>(r.base_req);
        this.scaling_req = new HashMap<>(r.scaling_req);

    }

    public static StatRequirement combine(StatRequirement one, StatRequirement two) {
        StatRequirement req = new StatRequirement(one);

        two.scaling_req.entrySet()
            .forEach(x -> {
                req.scaling_req.put(x.getKey(), req.scaling_req.getOrDefault(x.getKey(), 0F) + x.getValue());
            });
        two.base_req.entrySet()
            .forEach(x -> {
                req.base_req.put(x.getKey(), req.base_req.getOrDefault(x.getKey(), 0F) + x.getValue());
            });

        return req;
    }

    public StatRequirement() {
    }

    public boolean meetsReq(int lvl, EntityCap.UnitData data) {

        for (BaseCoreStat x : AllAttributes.getInstance()
            .coreStatsThatBenefit()) {

            int num = getReq(x, lvl);

            if (num > data.getUnit()
                .getCalculatedStat(x)
                .getAverageValue()) {
                return false;
            }

        }

        return true;

    }

    private int getReq(Stat stat, int lvl) {
        return (int) (base_req.getOrDefault(stat.GUID(), 0F) + StatScaling.STAT_REQ.scale(scaling_req.getOrDefault(stat.GUID(), 0F), lvl));
    }

    public StatRequirement setVit(float req) {
        this.scaling_req.put(Vitality.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setWis(float req) {

        this.scaling_req.put(Wisdom.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setAgi(float req) {
        this.scaling_req.put(Agility.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setDex(float req) {
        this.scaling_req.put(Dexterity.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setInt(float req) {
        this.scaling_req.put(Intelligence.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setStr(float req) {
        this.scaling_req.put(Strength.INSTANCE.GUID(), req);
        return this;
    }

    public StatRequirement setBaseDex(int req) {
        this.base_req.put(Dexterity.INSTANCE.GUID(), (float) req);
        return this;
    }

    public StatRequirement setBaseInt(int req) {
        this.base_req.put(Intelligence.INSTANCE.GUID(), (float) req);
        return this;
    }

    public StatRequirement setBaseStr(int req) {
        this.base_req.put(Strength.INSTANCE.GUID(), (float) req);
        return this;
    }

    public List<Text> GetTooltipString(int lvl, EntityCap.UnitData data) {
        List<Text> list = new ArrayList<>();

        for (BaseCoreStat x : AllAttributes.getInstance()
            .coreStatsThatBenefit()) {

            int num = getReq(x, lvl);

            if (num > 0) {
                list.add(getTooltip(num, x, data));

            }

        }

        return list;
    }

    public boolean isEmpty() {
        return this.base_req.isEmpty() && this.scaling_req.isEmpty();
    }

    static Text getTooltip(int req, Stat stat, EntityCap.UnitData data) {

        if (data.getUnit()
            .getCalculatedStat(stat)
            .getAverageValue() >= req) {
            return new LiteralText(Formatting.GREEN + "" + Formatting.BOLD + CHECK_YES_ICON + " ").append(stat.locName()
                .formatted(Formatting.GRAY))
                .append(" " + Formatting.GRAY + "Min: " + req + " ");
        } else {

            return new LiteralText(Formatting.RED + "" + Formatting.BOLD + NO_ICON + " ").append(stat.locName()
                .formatted(Formatting.GRAY))
                .append(" " + Formatting.GRAY + "Min: " + req + " ");

        }

    }

    @Override
    public Class<StatRequirement> getClassForSerialization() {
        return StatRequirement.class;
    }
}
