package com.robertx22.age_of_exile.aoe_data.database.value_calc;

import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class ValueCalcAdder implements ISlashRegistryInit {

    public static ValueCalculation BOLT_SPELL = ValueCalculation.base("default_bolt_dmg", 10);
    public static ValueCalculation DIRECT_ARROW_HIT = ValueCalculation.scaleWithAttack("direct_arrow_hit", 0.25F, 3);

    @Override
    public void registerAll() {

    }
}
