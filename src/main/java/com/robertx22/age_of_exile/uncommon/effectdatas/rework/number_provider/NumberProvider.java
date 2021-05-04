package com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
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

    public static NumberProvider ofPercentOfStat(String stat) {
        NumberProvider p = new NumberProvider();
        p.calc = stat;
        p.type = Type.perc_of_stat;
        return p;
    }

    public static NumberProvider ofPercentOfDataNumber(String numId) {
        NumberProvider p = new NumberProvider();
        p.calc = numId;
        p.type = Type.perc_of_num;
        return p;
    }

    public static NumberProvider ofStatData() {
        NumberProvider p = new NumberProvider();
        p.type = Type.stat_data;
        return p;
    }

    private Type type = Type.stat_data;
    private String calc = "";

    public float getValue(EffectEvent event, LivingEntity source, StatData data) {
        if (type == Type.stat_data) {
            return data.getAverageValue();
        } else if (type == Type.val_calc) {
            return Database.ValueCalculations()
                .get(calc)
                .getCalculatedValue(source, Load.Unit(source)
                    .getLevel());
        } else if (type == Type.perc_of_stat) {
            float val = Load.Unit(source)
                .getUnit()
                .getCalculatedStat(calc)
                .getAverageValue() * data.getAverageValue() / 100F;
            return val;
        } else if (type == Type.perc_of_num) {
            float val = event.data.getNumber(calc).number * data.getAverageValue() / 100F;
            return val;
        }

        throw new RuntimeException("No type?");
    }

    public enum Type {
        val_calc, stat_data, perc_of_stat, perc_of_num
    }

}
