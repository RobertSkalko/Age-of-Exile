package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.ArrayList;
import java.util.List;

public class DamageCalculation {

    public Elements element = Elements.Physical;
    public List<ScalingCalc> scaling = new ArrayList<>();
    public float base = 0;

    public int getCalculatedValue(EntityData data) {
        int val = getCalculatedScalingValue(data);
        val += getCalculatedBaseValue(data);
        return val;
    }

    private int getCalculatedScalingValue(EntityData data) {
        float amount = 0;
        amount += scaling.stream()
            .mapToInt(x -> x.getCalculatedValue(data))
            .sum();
        return (int) amount;
    }

    private int getCalculatedBaseValue(EntityData data) {
        return (int) StatScaling.NORMAL.scale(base, data.getLevel());
    }

    public static class Builder {
        DamageCalculation dmg = new DamageCalculation();

        public static Builder of(Elements element) {
            Builder b = new Builder();
            b.dmg.element = element;
            return b;
        }

        public Builder base(float val) {
            dmg.base = val;
            return this;
        }

        public Builder scaling(float scaling) {
            dmg.scaling.add(new ScalingCalc(new AttackDamage(dmg.element), scaling));
            return this;
        }

        public Builder stat(Stat stat, float scaling) {
            dmg.scaling.add(new ScalingCalc(stat, scaling));
            return this;
        }

        public DamageCalculation build() {
            return dmg;
        }

    }

}
