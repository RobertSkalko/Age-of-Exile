package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ValueCalcBuilder {
    ValueCalculation calc;

    public static ValueCalcBuilder of(String id) {
        ValueCalcBuilder b = new ValueCalcBuilder();
        b.calc = new ValueCalculation();
        b.calc.id = id;
        return b;
    }

    public static ValueCalcBuilder of(String id, String locname) {
        ValueCalcBuilder b = new ValueCalcBuilder();
        b.calc = new ValueCalculation();
        b.calc.locname = locname;
        b.calc.id = id;
        return b;
    }

    public static ValueCalcBuilder of(SpellKey key, String locname) {
        ValueCalcBuilder b = new ValueCalcBuilder();
        b.calc = new ValueCalculation();
        b.calc.locname = locname;
        b.calc.id = key.id;
        return b;
    }

    public ValueCalcBuilder baseValue(float val) {
        calc.base = val;
        return this;
    }

    public ValueCalcBuilder attackScaling(float scaling) {
        calc.attack_scaling = scaling;
        return this;
    }

    public ValueCalcBuilder statScaling(Stat stat, float scaling) {
        calc.stat_scalings.add(new ScalingCalc(stat, scaling));
        return this;
    }

    public ValueCalcBuilder damage(DamageCalculation dmg) {
        calc.damage_calcs.damages.add(dmg);
        return this;
    }

    public ValueCalcBuilder addAllElementsScaling(float scaling) {
        for (Elements ele : Elements.getAllSingleIncludingPhysical()) {
            if (calc.damage_calcs.damages.stream()
                .noneMatch(x -> x.element == ele)) {
                calc.damage_calcs.damages.add(DamageCalculation.Builder.of(ele)
                    .scaling(scaling)
                    .build());
                // add default sclaing of 1 to every spell
            }
        }
        return this;
    }

    public ValueCalculation build() {

        calc.addToSerializables();
        return calc;
    }
}
