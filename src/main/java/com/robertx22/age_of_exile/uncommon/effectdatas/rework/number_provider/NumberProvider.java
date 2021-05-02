package com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class NumberProvider {

    public NumberProvider() {
    }

    public static NumberProvider ofCalc(String id) {
        NumberProvider p = new NumberProvider();
        p.calc = id;
        p.type = Type.val_calc;
        return p;
    }

    public static NumberProvider ofStatData() {
        NumberProvider p = new NumberProvider();
        p.type = Type.stat_data;
        return p;
    }

    private Type type = Type.stat_data;
    private String calc = "";

    public float getValue(LivingEntity source, StatData data) {
        if (type == Type.stat_data) {
            return data.getAverageValue();
        } else if (type == Type.val_calc) {
            return Database.ValueCalculations()
                .get(calc)
                .getCalculatedValue(source, Load.Unit(source)
                    .getLevel());
        }

        throw new RuntimeException("No type?");
    }

    public enum Type {
        val_calc, stat_data
    }

}
