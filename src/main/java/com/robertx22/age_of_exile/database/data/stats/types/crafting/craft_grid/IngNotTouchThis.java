package com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.ArrayList;
import java.util.List;

public class IngNotTouchThis extends BaseCraftGridStat {

    public static IngNotTouchThis getInstance() {
        return IngNotTouchThis.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final IngNotTouchThis INSTANCE = new IngNotTouchThis();
    }

    public IngNotTouchThis() {
        super();
    }

    @Override
    public List<PointData> getIngredientsAffected(PointData point) {
        List<PointData> all = new ArrayList<>();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                all.add(new PointData(x, y));
            }

        }

        List<PointData> list = new ArrayList<>();

        list.add(point.up());
        list.add(point.down());
        list.add(point.left());
        list.add(point.right());
        list.add(point);

        all.removeAll(list);

        return all;
    }

    @Override
    public String locNameForLangFile() {
        return Stat.format("Ingredients not touching this one have " + Stat.VAL1 + " effectiveness.");
    }

    @Override
    public String GUID() {
        return "not_touch_this";
    }
}
