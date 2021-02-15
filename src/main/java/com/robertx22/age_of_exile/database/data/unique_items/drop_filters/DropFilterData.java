package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class DropFilterData {

    @Store
    public String type = "";
    @Store
    public String id = "";

    public static DropFilterData of(DropFilter filter, String id) {
        DropFilterData data = new DropFilterData();
        data.type = filter.GUID();
        data.id = id;
        return data;
    }
}
