package com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.ArrayList;
import java.util.List;

public class IngTouchThis extends BaseCraftGridStat {

    public static IngTouchThis getInstance() {
        return IngTouchThis.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final IngTouchThis INSTANCE = new IngTouchThis();
    }

    public IngTouchThis() {
        super();
    }

    @Override
    public List<PointData> getIngredientsAffected(PointData point) {
        List<PointData> list = new ArrayList<>();

        list.add(point.up());
        list.add(point.down());
        list.add(point.left());
        list.add(point.right());

        return list;
    }

    @Override
    public String locNameForLangFile() {
        return Stat.format(
            "Ingredients touching this one have " + Stat.VAL1 + " effectiveness."
        );
    }

    @Override
    public String GUID() {
        return "touch_this";
    }
}
