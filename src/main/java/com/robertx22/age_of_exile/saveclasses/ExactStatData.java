package com.robertx22.age_of_exile.saveclasses;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ExactStatData implements ISerializable<ExactStatData>, ITooltipList {

    public static ExactStatData EMPTY = new ExactStatData();

    @Factory
    private ExactStatData() {

    }

    public static ExactStatData of(float first, float second, Stat stat, ModType type, int lvl) {
        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.v2 = second;
        data.stat = stat.GUID();
        data.type = type;
        data.scaleToLevel(lvl);
        return data;
    }

    public static ExactStatData fromStatModifier(StatModifier mod, int percent, int lvl) {
        ExactStatData data = new ExactStatData();

        data.v1 = (mod.min1 + (mod.max1 - mod.min1) * percent / 100F);

        if (mod.usesNumberRanges()) {
            data.v2 = (mod.min2 + (mod.max2 - mod.min2) * percent / 100);
        } else {
            data.v2 = data.v1;
        }

        data.type = mod.getModType();
        data.stat = mod.stat;

        data.scaleToLevel(lvl);

        return data;
    }

    public static ExactStatData scaleTo(float value, ModType type, String stat_id, int level) {
        return scaleTo(value, value, type, stat_id, level);
    }

    public static ExactStatData scaleTo(float v1, float v2, ModType type, String stat, int level) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;
        data.v2 = v2;

        data.type = type;
        data.stat = stat;

        data.scaleToLevel(level);

        return data;
    }

    public void multiplyBy(float multi) {
        v1 *= multi;
        v2 *= multi;
    }

    public static ExactStatData noScaling(float v1, float v2, ModType type, String stat) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;
        data.v2 = v2;

        data.type = type;
        data.stat = stat;

        data.scaled = true;

        return data;
    }

    private boolean scaled = false;

    private void scaleToLevel(int lvl) {
        if (!scaled) {
            if (this.type.isFlat()) {
                this.v1 = getStat().scale(v1, lvl);
                this.v2 = getStat().scale(v2, lvl);
            }
        }
    }

    @Store
    private float v1 = 0;
    @Store
    private float v2 = 0;
    @Store
    private ModType type = ModType.FLAT;

    @Store
    private String stat = "";

    public transient float percentIncrease = 0;

    public String getStatId() {
        return stat;
    }

    public float getAverageValue() {
        return (v1 + v2) / 2F;
    }

    public boolean shouldBeAddedToLocalStats(GearItemData gear) {

        if (getStat()
            .isLocal()) {
            if (getType() != ModType.GLOBAL_INCREASE) {
                ILocalStat local = (ILocalStat) getStat();
                if (local.IsNativeToGearType(gear.GetBaseGearType())) {
                    return true;
                }
            }
        }

        return false;

    }

    public void add(ExactStatData other) {
        if (type == other.type) {
            v1 += other.v1;
            v2 += other.v2;
        } else {
            System.out.println("error wrong types");
        }
    }

    public void increaseByAddedPercent() {

        v1 += v1 * percentIncrease / 100F;

        if (v2 != 0) {
            v2 += v2 * percentIncrease / 100F;
        }

        percentIncrease = 0;
    }

    public float getFirstValue() {
        return v1;
    }

    public float getSecondValue() {
        return v2;
    }

    public ModType getType() {
        return type;
    }

    public Stat getStat() {
        return Database.Stats()
            .get(stat);
    }

    public void applyStats(EntityCap.UnitData data) {
        data.getUnit()
            .getStats()
            .getStatInCalculation(stat)
            .add(this, data);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this, 100, info);
        return new ArrayList<>(stat.getTooltipList(new TooltipStatWithContext(statInfo, null, null)));

    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("v1", this.v1);
        json.addProperty("v2", this.v2);
        json.addProperty("type", this.type.id);
        json.addProperty("stat", this.stat);

        return json;
    }

    @Override
    public ExactStatData fromJson(JsonObject json) {

        float first = json.get("v1")
            .getAsFloat();
        float second = json.get("v2")
            .getAsFloat();

        String stat = json.get("stat")
            .getAsString();

        ModType type = ModType.fromString(json.get("type")
            .getAsString());

        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.v2 = second;
        data.stat = stat;
        data.type = type;

        data.scaled = true;
        return data;
    }
}
