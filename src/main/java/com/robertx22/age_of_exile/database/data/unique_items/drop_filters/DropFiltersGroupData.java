package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.library_of_exile.registry.IAutoGson;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class DropFiltersGroupData {

    @Store
    public List<DropFilterData> list = new ArrayList<>();

    public boolean canDrop(LootInfo info) {

        if (info == null) {
            if (list.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        for (DropFilterData x : list) {
            if (!DropFilters.get(x.type)
                .canDrop(x, info)) {
                return false;
            }
        }
        return true;

    }

    public boolean cantDrop(LootInfo info) {
        return !canDrop(info);
    }

    public JsonElement toJson() {
        return new GsonBuilder().setPrettyPrinting()
            .create()
            .toJsonTree(this);
    }

    public static DropFiltersGroupData fromJson(JsonElement json) {
        return IAutoGson.GSON.fromJson(json, DropFiltersGroupData.class);
    }
}
