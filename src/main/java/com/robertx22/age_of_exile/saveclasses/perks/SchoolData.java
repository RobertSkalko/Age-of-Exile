package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.saveclasses.PointData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;
import java.util.Map;

@Storable
public class SchoolData {

    @Store
    HashMap<PointData, Boolean> map = new HashMap<>();

    public boolean isAllocated(PointData point) {
        return map.getOrDefault(point, false);
    }

    public int getAllocatedPointsInSchool() {

        int points = 0;

        for (Map.Entry<PointData, Boolean> x : map.entrySet()) {
            if (x.getValue()) {
                points++;
            }
        }

        return points;

    }

}
