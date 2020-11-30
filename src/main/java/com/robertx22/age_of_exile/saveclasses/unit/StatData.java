package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

@Storable
public class StatData {

    private static StatData empty = new StatData();

    public static StatData empty() {
        return empty;
    }

    public StatData(String id, float v1, float v2) {
        this.id = id;
        this.v1 = v1;
        this.v2 = v2;
    }

    public StatData() {

    }

    public StatData(Stat stat) {
        this.id = stat.GUID();
    }

    public Stat GetStat() {
        return Database.Stats()
            .get(id);
    }

    @Store// guid
    private String id = "";
    @Store
    private float v1 = 0;
    @Store
    private float v2 = 0;

    public String toSerializationString() {
        return id + ":" + v1 + ":" + v2;
    }

    public static StatData fromSerializationString(String str) {
        String[] parts = str.split(":");

        String id = parts[0];
        float v1 = Float.parseFloat(parts[1]);
        float v2 = Float.parseFloat(parts[2]);

        return new StatData(id, v1, v2);
    }

    public String getId() {
        return id;
    }

    public float getFirstValue() {
        return v1;
    }

    public float getSecondValue() {
        return v2;
    }

    public float getAverageValue() {

        float val = (getFirstValue() + getSecondValue()) / 2F;

        Stat stat = GetStat();
        return MathHelper.clamp(val, stat.min_val, stat.max_val);
    }

    public boolean isNotZero() {
        return v1 != 0;
    }

    public float getRandomRangeValue() {
        return RandomUtils.RandomRange(getFirstValue(), getSecondValue());
    }

    public float getMultiplier() {
        return 1F + getAverageValue() / 100F;
    }

    public float getReverseMultiplier() {
        return 1 - getAverageValue() / 100;
    }

}
