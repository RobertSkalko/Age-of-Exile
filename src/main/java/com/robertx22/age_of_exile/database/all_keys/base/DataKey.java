package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.library_of_exile.registry.IGUID;

import java.util.ArrayList;
import java.util.Objects;

public abstract class DataKey<T> implements IGUID {

    public String id;

    public DataKey(String id) {
        this.id = id;

        if (!AllDataKeys.ALL.containsKey(getDataClass())) {
            AllDataKeys.ALL.put(getDataClass(), new ArrayList<>());
        }

        AllDataKeys.ALL.get(getDataClass())
            .add(this);

    }

    @Override
    public String GUID() {
        return id;
    }

    public abstract T getFromRegistry();

    public abstract T getFromDataGen();

    public abstract Class getDataClass();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataKey dataKey = (DataKey) o;
        return Objects.equals(id, dataKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
