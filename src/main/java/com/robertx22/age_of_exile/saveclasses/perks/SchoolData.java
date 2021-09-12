package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.saveclasses.PointData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Storable
public class SchoolData {

    @Store
    HashMap<PointData, Boolean> map = new HashMap<>();

    public boolean isAllocated(PointData point) {
        return map.getOrDefault(point, false);
    }

    public List<PointData> getAllocatedPoints(TalentTree school) {
        return map.entrySet()
            .stream()
            .filter(x -> {
                return x.getValue() && school.calcData.getPerk(x.getKey()) != null;
            })
            .map(x -> x.getKey())
            .collect(Collectors.toList());
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
