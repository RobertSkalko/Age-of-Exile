package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;

import java.util.HashMap;

public class DataPackStatAccessor<T> {

    private HashMap<T, String> map = new HashMap<>();
    private HashMap<T, Stat> map2 = new HashMap<>();

    public Stat get(T key) {
        if (!Database.Stats()
            .isRegistered(getId(key))) {
            return map2.get(key);
        }
        return Database.Stats()
            .get(map.get(key));
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
        return get((T) EmptyAccessor.INSTANCE);
    }
}
