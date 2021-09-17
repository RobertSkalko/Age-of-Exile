package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
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
    private float Percent = 0;
    private float Multi = 0;
    private boolean calc = false;

    @Factory
    private InCalcStatData() {

    }

    public InCalcStatData cloneForSpellStats() {
        InCalcStatData clone = new InCalcStatData();
        clone.id = id;
        clone.Flat = Flat;
        clone.Percent = Percent;
        clone.Multi = Multi;

        return clone;
    }

    public InCalcStatData(String id) {
        this.id = id;
    }

    public void clear() {
        this.Flat = 0;
        this.Percent = 0;
        this.Multi = 0;
    }

    private float calcValue() {
        Stat stat = this.GetStat();

        float finalValue = stat.base;

        finalValue += Flat;

        finalValue *= 1 + Percent / 100;

        finalValue *= 1 + Multi / 100;

        this.calc = true;
        return MathHelper.clamp(finalValue, stat.min, stat.max);

    }


    public Stat GetStat() {
        return ExileDB.Stats()
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
    }

    public void addFullyTo(InCalcStatData other) {
        other.Flat += Flat;
        other.Percent += Percent;
        other.Multi += Multi;

    }

    public void addFlat(float val1, int lvl) {
        this.Flat += GetStat().scale(val1, lvl);
    }

    public boolean isMoreThanZero() {
        return Flat > 0;
    }

    public boolean isNotEmpty() {
        return Flat != 0 || Percent != 0 || Multi != 0;
    }

    public void multiplyFlat(float multi) {
        this.Flat *= multi;
    }

    public void multiplyFlat(double multi) {
        this.Flat *= multi;
    }

    public void add(ExactStatData modData, EntityCap.UnitData data) {
        ModType type = modData.getType();

        float v1 = modData.getFirstValue();

        float v = v1;

        if (type == ModType.FLAT) {
            Flat += v1;
        } else if (type == ModType.PERCENT) {
            Percent += v;
        } else if (type == ModType.GLOBAL_INCREASE) {
            Multi += v;
        }

    }

    public float getValue() {
        return Flat;
    }

    public StatData getCalculated() {

        if (!calc) {
            this.calcValue();
        }
        return new StatData(this.id, getValue());
    }
}
