package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;

@Storable
public class StatData {

    private static StatData empty = new StatData();

    public static StatData empty() {
        return empty;
    }

    public StatData(String id, float v1) {
        this.id = id;
        this.v1 = v1;
    }

    public StatData() {

    }

    public StatData(Stat stat) {
        this.id = stat.GUID();
    }

    public Stat GetStat() {
        return ExileDB.Stats()
            .get(id);
    }

    @Store// guid
    private String id = "";
    @Store
    private float v1 = 0;

    public String toSerializationString() {
        return id + ":" + v1;
    }

    public static StatData fromSerializationString(String str) {
        String[] parts = str.split(":");
        String id = parts[0];
        float v1 = Float.parseFloat(parts[1]);
        return new StatData(id, v1);
    }

    public String getId() {
        return id;
    }

    public float getValue() {
        Stat stat = GetStat();
        return MathHelper.clamp(v1, stat.min, stat.max);
    }

    public boolean isNotZero() {
        return v1 != 0;
    }

    public float getMultiplier() {
        return 1F + getValue() / 100F;
    }

    public float getReverseMultiplier() {
        return 1 - getValue() / 100;
    }

}
