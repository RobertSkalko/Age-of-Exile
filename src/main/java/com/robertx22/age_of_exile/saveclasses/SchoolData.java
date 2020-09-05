package com.robertx22.age_of_exile.saveclasses;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SchoolData {

    @Store
    List<PointData> list = new ArrayList<>();

    public boolean isAllocated(PointData point) {
        return list.contains(point);
    }

}
