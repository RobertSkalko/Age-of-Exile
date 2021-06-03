package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.value_calc.ScalingStatCalculation;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;

public class ValueCalcs {

    public static void init() {

    }

    public static ValueCalculation FIREBALL = ValueCalculation.base("fireball", 8);
    public static ValueCalculation HEALING_AURA = ValueCalculation.base("healing_aura", 4);
    public static ValueCalculation FROST_NOVA = ValueCalculation.base("frost_nova", 7);
    public static ValueCalculation POISON_BALL = ValueCalculation.base("poisonball", 8);
    public static ValueCalculation ICEBALL = ValueCalculation.base("iceball", 8);
    public static ValueCalculation SHOUT_WARN = ValueCalculation.scaleWithStat("shout_warn", new ScalingStatCalculation(Health.getInstance(), 0.05F), 20);
    public static ValueCalculation CHARGED_BOLT = ValueCalculation.scaleWithAttack("charged_bolt", 0.2F, 1);
    public static ValueCalculation EXECUTE = ValueCalculation.scaleWithAttack("execute", 2F, 0);

}
