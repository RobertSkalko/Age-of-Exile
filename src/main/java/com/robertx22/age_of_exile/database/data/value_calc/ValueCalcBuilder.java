package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;

public class ValueCalcBuilder {
    ValueCalculation calc;

    public static ValueCalcBuilder of(String id) {
        ValueCalcBuilder b = new ValueCalcBuilder();
        b.calc = new ValueCalculation();
        b.calc.id = id;
        return b;
    }

    public ValueCalcBuilder baseValue(float min, float max) {
        calc.base = new LeveledValue(min, max);
        return this;
    }

    public ValueCalcBuilder attackScaling(float min, float max) {
        calc.attack_scaling = new LeveledValue(min, max);
        return this;
    }

    public ValueCalcBuilder spellScaling(float min, float max) {
        return statScaling(SpellDamage.getInstance(), min, max); // todo test CHANGE THIS TO FLAT
    }

    public ValueCalcBuilder statScaling(Stat stat, float min, float max) {
        calc.stat_scalings.add(new ScalingCalc(stat, new LeveledValue(min, max)));
        return this;
    }

    public ValueCalculation build() {
        calc.addToSerializables();
        return calc;
    }
}
