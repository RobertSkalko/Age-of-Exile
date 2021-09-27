package com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.ArrayList;
import java.util.List;

public class IngToLeft extends BaseCraftGridStat {

    public static IngToLeft getInstance() {
        return IngToLeft.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final IngToLeft INSTANCE = new IngToLeft();
    }

    public IngToLeft() {
        super();
    }

    @Override
    public List<PointData> getIngredientsAffected(PointData point) {
        List<PointData> all = new ArrayList<>();
        all.add(point.left());
        all.add(point.left().left());
        return all;
    }

    @Override
    public String locNameForLangFile() {
        return Stat.format("Ingredients to the left of this one have " + Stat.VAL1 + " effectiveness.");
    }

    @Override
    public String GUID() {
        return "left_of_this";
    }
}
