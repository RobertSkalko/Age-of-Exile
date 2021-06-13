package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.value_calc.ScalingCalc;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;

public class SpellCalcs {

    public static void init() {

    }

    public static ValueCalculation FIREBALL = ValueCalculation.base("fireball", 8);
    public static ValueCalculation HEALING_AURA = ValueCalculation.base("healing_aura", 4);
    public static ValueCalculation FROST_NOVA = ValueCalculation.base("frost_nova", 7);
    public static ValueCalculation POISON_BALL = ValueCalculation.base("poisonball", 8);
    public static ValueCalculation ICEBALL = ValueCalculation.base("iceball", 8);
    public static ValueCalculation SHOUT_WARN = ValueCalculation.withStat("shout_warn", new ScalingCalc(Health.getInstance(), 0.05F), 20);
    public static ValueCalculation CHARGED_BOLT = ValueCalculation.scaleWithAttack("charged_bolt", 0.2F, 1);
    public static ValueCalculation EXECUTE = ValueCalculation.base("execute", 20);
    public static ValueCalculation CHARGE = ValueCalculation.scaleWithAttack("charge", 1F, 0);
    public static ValueCalculation TAUNT = ValueCalculation.withStat("taunt", new ScalingCalc(Health.getInstance(), 0.05F), 10);
    public static ValueCalculation PULL = ValueCalculation.base("pull", 5);
    public static ValueCalculation SHRED = ValueCalculation.base("shred", 9);
    public static ValueCalculation TOTEM_HEAL = ValueCalculation.base("totem_heal", 3);
    public static ValueCalculation TOTEM_GUARD = ValueCalculation.base("totem_guard", 5);
    public static ValueCalculation TOTEM_MANA = ValueCalculation.base("totem_mana", 5);
    public static ValueCalculation CURSE = ValueCalculation.base("curse", 3);
    public static ValueCalculation STORM_CALL = ValueCalculation.base("storm_call", 7);
    public static ValueCalculation BLACK_HOLE = ValueCalculation.base("black_hole", 5);

}
