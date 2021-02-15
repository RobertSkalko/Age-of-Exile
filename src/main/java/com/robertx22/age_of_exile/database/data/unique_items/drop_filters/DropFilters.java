package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import java.util.HashMap;

public class DropFilters {

    static HashMap<String, DropFilter> map = new HashMap<>();

    static {

        map.put(new MobTagFilter().GUID(), new MobTagFilter());
        map.put(new SpecificMobFilter().GUID(), new SpecificMobFilter());

    }

    public static DropFilter get(String id) {
        return map.get(id);
    }
}
