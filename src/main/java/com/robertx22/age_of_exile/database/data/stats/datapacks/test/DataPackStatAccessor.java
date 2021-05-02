package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;

import java.util.HashMap;
import java.util.Objects;

public class DataPackStatAccessor<T> {

    private HashMap<T, String> map = new HashMap<>();
    private HashMap<T, Stat> map2 = new HashMap<>();

    public Stat get(T key) {
        Stat stat;

        if (!Database.Stats()
            .isRegistered(getId(key))) {
            stat = map2.get(key);
            Objects.requireNonNull(stat, "Null for " + key.toString());
            return stat;
        }

        stat = Database.Stats()
            .get(map.get(key));
        Objects.requireNonNull(stat, "Null for " + key.toString());
        return stat;
    }

    public String getId(T key) {
        return map.get(key);
    }

    public String getId() {
        return getId((T) EmptyAccessor.INSTANCE);
    }

    public void add(T key, DatapackStat stat) {
        map.put(key, stat.id);
        map2.put(key, stat);
    }

    public Stat get() {
        if (this.map.size() > 1) {
            throw new RuntimeException("Using simple .get() for stat that isn't just a single stat!");
        }
        return get((T) EmptyAccessor.INSTANCE);
    }
}
