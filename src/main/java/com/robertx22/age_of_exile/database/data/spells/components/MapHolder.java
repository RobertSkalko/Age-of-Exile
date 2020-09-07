package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;

public class MapHolder {

    public String type;

    private HashMap<String, Object> map = new HashMap<>();

    public <T> void put(MapField<T> field, T t) {
        this.map.put(field.GUID(), t);
    }

    public <T> T get(MapField<T> field) {
        return (T) map.get(field.GUID());
    }

}
