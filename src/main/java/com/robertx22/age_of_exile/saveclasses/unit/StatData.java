package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.IAddToOtherStats;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

@Storable
public class StatData {

    private static StatData empty = new StatData();

    public static StatData empty() {
        return empty;
    }

    public StatData() {

    }

    public StatData(Stat stat) {
        this.id = stat.GUID();
    }

    public Stat GetStat() {
        return SlashRegistry.Stats()
            .get(id);
    }

    @Store// guid
    private String id = "";

    private float Flat = 0;
    private float Flat2 = 0;
    private float Percent = 0;
    private float Multi = 0;

    @Store
    private float v1 = 0;
    @Store
    private float v2 = 0;

    public String toSerializationString() {
        return id + ":" + v1 + ":" + v2;
    }

    public static StatData fromSerializationString(String str) {

        StatData obj = new StatData();

        String[] parts = str.split(":");
        obj.id = parts[0];
        obj.v1 = Float.parseFloat(parts[1]);
        obj.v2 = Float.parseFloat(parts[2]);

        return obj;

    }

    public void addAlreadyScaledFlat(float val1) {
        this.Flat += val1;
        this.Flat2 += val1;
    }

    public void addAlreadyScaledFlat(float val1, float val2) {
        this.Flat += val1;
        this.Flat2 += val2;
    }

    public void addFlat(float val1, int lvl) {
        this.Flat += GetStat().scale(val1, lvl);
        this.Flat2 += GetStat().scale(val1, lvl);
    }

    public float getFlatAverage() {
        return (Flat + Flat2) / 2;
    }

    public void CalcVal() {

        calcFirstValue();
        calcSecondValue();

    }

    private void calcFirstValue() {
        Stat stat = this.GetStat();

        if (stat.isTrait()) {
            if (Flat > 0) {
                v1 = 1;

            } else {
                v1 = 0;
            }
            return;
        } else {
            float finalValue = stat.BaseFlat;

            finalValue += Flat;

            finalValue *= 1 + Percent / 100;

            finalValue *= 1 + Multi / 100;

            v1 = MathHelper.clamp(finalValue, stat.minimumValue, stat.maximumValue);

        }
    }

    private void calcSecondValue() {
        Stat stat = this.GetStat();

        if (stat.isTrait()) {
            if (Flat2 > 0) {
                v2 = 1;

            } else {
                v2 = 0;

            }
            return;
        } else {
            float finalValue = stat.BaseFlat;

            finalValue += Flat2;

            finalValue *= 1 + Percent / 100;

            finalValue *= 1 + Multi / 100;

            v2 = MathHelper.clamp(finalValue, stat.minimumValue, stat.maximumValue);

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(float val) {
        Stat stat = GetStat();

        this.v1 = MathHelper.clamp(val, stat.minimumValue, stat.maximumValue);

    }

    public void addMulti(float multi) {
        this.Multi += multi;
    }

    public void addPercent(float percent) {
        this.Percent += percent;
    }

    public float getFirstValue() {
        return v1;
    }

    public float getSecondValue() {
        return v2;
    }

    public float getAverageValue() {
        float val = (getFirstValue() + getSecondValue()) / 2;

        Stat stat = GetStat();
        return MathHelper.clamp(val, stat.minimumValue, stat.maximumValue);
    }

    public boolean isNotZero() {
        return v1 != 0 && v2 != 0;
    }

    public float getRandomRangeValue() {
        return RandomUtils.RandomRange(getFirstValue(), getSecondValue());
    }

    public boolean isMoreThanZero() {
        return Flat > 0;
    }

    public void add(ExactStatData modData, EntityCap.UnitData data) {
        ModType type = modData.getType();

        Float v1 = modData.getFirstValue();
        Float v2 = modData.getSecondValue();

        Float v = (v1 + v2) / 2;

        if (type == ModType.FLAT) {
            Flat += v1;
            Flat2 += v2;
        } else if (type == ModType.LOCAL_INCREASE && !this.GetStat()
            .isLocal()) {
            Percent += v;
        } else if (type == ModType.GLOBAL_INCREASE) {
            Multi += v;
        }

        if (data != null && GetStat() instanceof IAddToOtherStats) {
            IAddToOtherStats add = (IAddToOtherStats) GetStat();
            add.addToOtherStats(data, v1, v2);
            // good reason why this is here. stat requirements..
        }

    }

    public void transferAllPreCalcTo(StatData other) {
        addFullyTo(other);

        this.Clear();
        this.v1 = 0;
        this.v2 = 0;
    }

    public void addFullyTo(StatData other) {
        other.Flat += Flat;
        other.Flat2 += Flat2;
        other.Percent += Percent;
        other.Multi += Multi;

    }

    public void addCalcValuesTo(StatData other) {
        other.v1 += v1;
        other.v2 += v2;

    }

    public void Clear() {
        Flat = 0;
        Flat2 = 0;
        Percent = 0;
        Multi = 0;
        v1 = 0;
        v2 = 0;
    }

    public String formattedValue() {

        float val = this.getAverageValue();

        DecimalFormat format = new DecimalFormat();

        if (Math.abs(val) < 10) {
            format.setMaximumFractionDigits(1);

            return format.format(val);

        } else {
            int intval = (int) val;
            return intval + "";
        }

    }

    public float getMultiplier() {
        return 1 + getAverageValue() / 100;
    }

    public float getReverseMultiplier() {
        return 1 - getAverageValue() / 100;
    }

    public boolean isNotEmpty() {
        return Flat != 0 || v1 != 0 || Percent != 0 || Multi != 0;
    }

    public void multiplyFlat(float multi) {
        this.Flat *= multi;
        this.Flat2 *= multi;
    }

    public void multiplyFlat(double multi) {
        this.Flat *= multi;
        this.Flat2 *= multi;
    }

}
