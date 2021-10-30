package com.robertx22.age_of_exile.saveclasses;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ExactStatData implements ISerializable<ExactStatData>, ITooltipList {

    public static ExactStatData EMPTY = new ExactStatData();

    @Factory
    private ExactStatData() {

    }

    public static ExactStatData of(float first, Stat stat, ModType type, int lvl) {
        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.stat = stat.GUID();
        data.type = type;
        data.scaleToLevel(lvl);
        return data;
    }

    public static ExactStatData fromStatModifier(StatModifier mod, int percent, float lvl) {
        ExactStatData data = new ExactStatData();

        data.v1 = (mod.min + (mod.max - mod.min) * percent / 100F);

        data.type = mod.getModType();
        data.stat = mod.stat;

        data.scaleToLevel(lvl);

        return data;
    }

    public static ExactStatData scaleTo(float v1, ModType type, String stat, int level) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;

        data.type = type;
        data.stat = stat;

        data.scaleToLevel(level);

        return data;
    }

    public void multiplyBy(float multi) {
        v1 *= multi;
    }

    public static ExactStatData noScaling(float v1, ModType type, String stat) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;

        data.type = type;
        data.stat = stat;

        data.scaled = true;

        return data;
    }

    private boolean scaled = false;

    private void scaleToLevel(float lvl) {
        if (!scaled) {
            this.v1 = getStat().scale(type, v1, lvl);
        }
    }

    @Store
    private float v1 = 0;
    @Store
    private ModType type = ModType.FLAT;

    @Store
    private String stat = "";

    public transient float percentIncrease = 0;

    public String getStatId() {
        return stat;
    }

    public float getAverageValue() {
        return (v1) / 2F;
    }

    public void add(ExactStatData other) {
        if (type == other.type) {
            v1 += other.v1;
        } else {
            System.out.println("error wrong types");
        }
    }

    public void increaseByAddedPercent() {

        v1 += v1 * percentIncrease / 100F;

        percentIncrease = 0;
    }

    public float getFirstValue() {
        return v1;
    }

    public ModType getType() {
        return type;
    }

    public Stat getStat() {
        return ExileDB.Stats()
            .get(stat);
    }

    public void applyStats(EntityData data) {
        data.getUnit()
            .getStats()
            .getStatInCalculation(stat)
            .add(this, data);
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {

        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this, 100, info);
        return new ArrayList<>(stat.getTooltipList(new TooltipStatWithContext(statInfo, null, null)));

    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("v1", this.v1);
        json.addProperty("type", this.type.id);
        json.addProperty("stat", this.stat);

        return json;
    }

    @Override
    public ExactStatData fromJson(JsonObject json) {

        float first = json.get("v1")
            .getAsFloat();
        String stat = json.get("stat")
            .getAsString();

        ModType type = ModType.fromString(json.get("type")
            .getAsString());

        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.stat = stat;
        data.type = type;

        data.scaled = true;
        return data;
    }

    public static void combine(List<ExactStatData> list) {

        List<ExactStatData> combined = new ArrayList<>();

        ExactStatData current = null;

        int i = 0;

        while (!list.isEmpty()) {

            List<ExactStatData> toRemove = new ArrayList<>();

            for (ExactStatData stat : list) {

                if (i == 0) {
                    toRemove.add(stat);
                    current = ExactStatData.of(stat.v1, stat.getStat(), stat.getType(), 1);
                    i++;
                    continue;
                }

                if (current.stat.equals(stat.stat)) {
                    if (current.type.equals(stat.type)) {
                        if (current.scaled == stat.scaled) {
                            current.v1 += stat.v1;
                            toRemove.add(stat);
                        }
                    }
                }

                i++;

            }

            i = 0;
            combined.add(current);
            toRemove.forEach(n -> list.removeAll(toRemove));

        }

        list.addAll(combined);

    }
}
