package com.robertx22.age_of_exile.saveclasses;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ExactStatData implements ISerializable<ExactStatData>, IApplyableStats, ITooltipList {

    public static ExactStatData EMPTY = new ExactStatData();

    @Factory
    private ExactStatData() {

    }

    public static ExactStatData fromStatModifier(StatModifier mod, int percent, int lvl) {
        ExactStatData data = new ExactStatData();

        data.first_val = (mod.firstMin + (mod.firstMax - mod.firstMin) * percent / 100F);

        if (mod.usesNumberRanges()) {
            data.second_val = (mod.secondMin + (mod.secondMax - mod.secondMin) * percent / 100);
        } else {
            data.second_val = data.first_val;
        }

        data.type = mod.getModType();
        data.stat_id = mod.stat;

        data.scaleToLevel(lvl);

        return data;
    }

    public static ExactStatData scaleTo(float value, ModType type, String stat_id, int level) {
        return scaleTo(value, value, type, stat_id, level);
    }

    public static ExactStatData scaleTo(float v1, float v2, ModType type, String stat, int level) {
        ExactStatData data = new ExactStatData();

        data.first_val = v1;
        data.second_val = v2;

        data.type = type;
        data.stat_id = stat;

        data.scaleToLevel(level);

        return data;
    }

    public static ExactStatData noScaling(float v1, float v2, ModType type, String stat) {
        ExactStatData data = new ExactStatData();

        data.first_val = v1;
        data.second_val = v2;

        data.type = type;
        data.stat_id = stat;

        data.scaled = true;

        return data;
    }

    private boolean scaled = false;

    private void scaleToLevel(int lvl) {
        if (!scaled) {
            if (this.type.isFlat()) {
                this.first_val = getStat().scale(first_val, lvl);
                this.second_val = getStat().scale(second_val, lvl);
            }
        }
    }

    @Store
    private float first_val = 0;
    @Store
    private float second_val = 0;
    @Store
    private ModType type = ModType.FLAT;

    @Store
    private String stat_id = "";

    public float percentIncrease = 0;

    public float getAverageValue() {
        return (first_val + second_val) / 2F;
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
            first_val += other.first_val;
            second_val += other.second_val;
        } else {
            System.out.println("error wrong types");
        }
    }

    public void increaseByAddedPercent() {

        first_val += first_val * percentIncrease / 100F;

        if (second_val != 0) {
            second_val += second_val * percentIncrease / 100F;
        }

        percentIncrease = 0;
    }

    public float getFirstValue() {
        return first_val;
    }

    public float getSecondValue() {
        return second_val;
    }

    public ModType getType() {
        return type;
    }

    public Stat getStat() {
        return SlashRegistry.Stats()
            .get(stat_id);
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        data.getUnit()
            .getCreateStat(stat_id)
            .add(this, data);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        if (first_val == 0) {
            // return new ArrayList<>();
        }

        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this, info);
        return new ArrayList<>(stat.getTooltipList(statInfo));

    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("first_val", this.first_val);
        json.addProperty("second_val", this.second_val);
        json.addProperty("type", this.type.id);
        json.addProperty("stat", this.stat_id);

        return json;
    }

    @Override
    public ExactStatData fromJson(JsonObject json) {

        float first = json.get("first_val")
            .getAsFloat();
        float second = json.get("second_val")
            .getAsFloat();

        String stat = json.get("stat")
            .getAsString();

        ModType type = ModType.fromString(json.get("type")
            .getAsString());

        ExactStatData data = new ExactStatData();
        data.first_val = first;
        data.second_val = second;
        data.stat_id = stat;
        data.type = type;

        data.scaled = true;
        return data;
    }
}
