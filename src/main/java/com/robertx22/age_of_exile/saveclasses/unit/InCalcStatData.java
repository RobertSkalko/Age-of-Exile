package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.IAddToOtherStats;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

@Storable
public class InCalcStatData {

    @Store// guid
    public String id = "";

    private float Flat = 0;
    private float Flat2 = 0;
    private float Percent = 0;
    private float Multi = 0;

    @Factory
    private InCalcStatData() {

    }

    public InCalcStatData(String id) {
        this.id = id;
    }

    private float calcFirstValue() {
        Stat stat = this.GetStat();

        if (stat.isTrait()) {
            if (Flat > 0) {
                return 1F;

            } else {
                return 0F;
            }

        } else {
            float finalValue = stat.base_val;

            finalValue += Flat;

            finalValue *= 1 + Percent / 100;

            finalValue *= 1 + Multi / 100;

            return MathHelper.clamp(finalValue, stat.min_val, stat.max_val);

        }
    }

    private float calcSecondValue() {
        Stat stat = this.GetStat();

        if (stat.isTrait()) {
            if (Flat2 > 0) {
                return 1F;

            } else {
                return 0F;
            }
        } else {
            float finalValue = stat.base_val;

            finalValue += Flat2;

            finalValue *= 1 + Percent / 100;

            finalValue *= 1 + Multi / 100;

            return MathHelper.clamp(finalValue, stat.min_val, stat.max_val);
        }
    }

    public Stat GetStat() {
        return SlashRegistry.Stats()
            .get(id);
    }

    public void addMulti(float multi) {
        this.Multi += multi;
    }

    public void addPercent(float percent) {
        this.Percent += percent;
    }

    public void addAlreadyScaledFlat(float val1) {
        this.Flat += val1;
        this.Flat2 += val1;
    }

    public void addFullyTo(InCalcStatData other) {
        other.Flat += Flat;
        other.Flat2 += Flat2;
        other.Percent += Percent;
        other.Multi += Multi;

    }

    public void addAlreadyScaledFlat(float val1, float val2) {
        this.Flat += val1;
        this.Flat2 += val2;
    }

    public void addFlat(float val1, int lvl) {
        this.Flat += GetStat().scale(val1, lvl);
        this.Flat2 += GetStat().scale(val1, lvl);
    }

    public boolean isMoreThanZero() {
        return Flat > 0;
    }

    public boolean isNotEmpty() {
        return Flat != 0 || Percent != 0 || Multi != 0;
    }

    public void multiplyFlat(float multi) {
        this.Flat *= multi;
        this.Flat2 *= multi;
    }

    public void multiplyFlat(double multi) {
        this.Flat *= multi;
        this.Flat2 *= multi;
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

    public float getFlatAverage() {
        return (Flat + Flat2) / 2;
    }

    public StatData getCalculated() {
        return new StatData(this.id, calcFirstValue(), calcSecondValue());
    }
}
