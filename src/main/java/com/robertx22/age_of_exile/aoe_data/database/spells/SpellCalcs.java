package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.value_calc.DamageCalculation;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalcBuilder;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class SpellCalcs {

    public static void init() {

    }

    public static ValueCalculation METEOR = ValueCalcBuilder.of("meteor")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(10)
            .scaling(2F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(10)
            .scaling(2F)
            .build())
        .addAllElementsScaling(1.5F)
        .build();

    public static ValueCalculation FROST_NOVA = ValueCalcBuilder.of("frost_nova")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(8)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(8)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1)
        .build();

    public static ValueCalculation MAGIC_PROJECTILE = ValueCalcBuilder.of("magic_projectile")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .scaling(1)
            .build())
        .addAllElementsScaling(0.8F)
        .build();

    public static ValueCalculation POISON = ValueCalcBuilder.of("poison")
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(2)
            .build())
        .build();

    // todo redo all these
    public static ValueCalculation BLEED = ValueCalcBuilder.of("bleed")
        .baseValue(4, 4)
        .build();

    public static ValueCalculation BURN = ValueCalcBuilder.of("burn")
        .baseValue(4, 4)
        .build();

    public static ValueCalculation PETRIFY = ValueCalcBuilder.of("petrify")
        .baseValue(5, 10)
        .build();

    public static ValueCalculation TORMENT = ValueCalcBuilder.of("torment")
        .baseValue(2, 6)
        .build();
    public static ValueCalculation DESPAIR = ValueCalcBuilder.of("despair")
        .baseValue(2, 6)
        .build();

    public static ValueCalculation WHIRLWIND = ValueCalcBuilder.of("whirlwind")
        .attackScaling(0.2F, 0.6F)
        .build();
    public static ValueCalculation BREATH = ValueCalcBuilder.of("breath")
        .spellScaling(0.2F, 0.6F)
        .build();

    public static ValueCalculation ARROW_STORM = ValueCalcBuilder.of("arrow_storm")
        .attackScaling(0.3F, 0.6F)
        .build();
    public static ValueCalculation EXPLOSIVE_ARROW = ValueCalcBuilder.of("explosive_arrow")
        .attackScaling(0.5F, 1.5F)
        .build();
    public static ValueCalculation RANGER_TRAP = ValueCalcBuilder.of("ranger_trap")
        .attackScaling(0.5F, 1F)
        .build();
    public static ValueCalculation AWAKEN_MANA = ValueCalcBuilder.of("awaken_mana")
        .baseValue(20, 100)
        .build();
    public static ValueCalculation HUNTER_POTION_HEAL = ValueCalcBuilder.of("hunter_pot_heal")
        .baseValue(20, 100)
        .build();
    public static ValueCalculation WISH = ValueCalcBuilder.of("wish")
        .baseValue(20, 80)
        .build();
    public static ValueCalculation NATURE_BALM = ValueCalcBuilder.of("nature_balm")
        .baseValue(1, 5)
        .build();

    public static ValueCalculation POWER_CHORD = ValueCalcBuilder.of("power_chord")
        .spellScaling(0.5F, 1F)
        .build();
    public static ValueCalculation SHOOTING_STAR = ValueCalcBuilder.of("shooting_star")
        .spellScaling(0.5F, 1.25F)
        .build();

    public static ValueCalculation HEALING_AURA = ValueCalcBuilder.of("healing_aura")
        .baseValue(4, 6)
        .build();
    public static ValueCalculation HEART_OF_ICE = ValueCalcBuilder.of("heart_of_ice")
        .baseValue(10, 50)
        .build();

    public static ValueCalculation CHILL_ERUPTION = ValueCalcBuilder.of("chill_eruption")
        .spellScaling(1F, 2.5F)
        .build();

    public static ValueCalculation POISON_CLOUD = ValueCalcBuilder.of("poison_cloud")
        .baseValue(2, 4)
        .spellScaling(0.2F, 0.4F)
        .build();

    public static ValueCalculation SHOUT_WARN = ValueCalcBuilder.of("shout_warn")
        .statScaling(Health.getInstance(), 0.05F, 0.1F)
        .build();
    public static ValueCalculation CHARGED_BOLT = ValueCalcBuilder.of("charged_bolt")
        .attackScaling(0.5F, 1F)
        .build();
    public static ValueCalculation CHARGE = ValueCalcBuilder.of("charge")
        .attackScaling(0.3F, 0.6F)
        .build();
    public static ValueCalculation TAUNT = ValueCalcBuilder.of("taunt")
        .statScaling(Health.getInstance(), 0.05F, 0.1F)
        .build();
    public static ValueCalculation PULL = ValueCalcBuilder.of("pull")
        .attackScaling(0.2F, 0.3F)
        .build();
    public static ValueCalculation SHRED = ValueCalcBuilder.of("shred")
        .attackScaling(0.3F, 0.6F)
        .build();
    public static ValueCalculation TOTEM_HEAL = ValueCalcBuilder.of("totem_heal")
        .baseValue(2, 3)
        .build();
    public static ValueCalculation TOTEM_GUARD = ValueCalcBuilder.of("totem_guard")
        .baseValue(3, 6)
        .build();
    public static ValueCalculation TOTEM_MANA = ValueCalcBuilder.of("totem_mana")
        .baseValue(3, 6)
        .build();
    public static ValueCalculation CURSE = ValueCalcBuilder.of("curse")
        .spellScaling(0.2F, 0.6F)
        .build();
    public static ValueCalculation BLACK_HOLE = ValueCalcBuilder.of("black_hole")
        .spellScaling(0.2F, 1F)
        .build();
    public static ValueCalculation CHILLING_FIELD = ValueCalcBuilder.of("chilling_field")
        .spellScaling(0.2F, 0.5F)
        .build();
    public static ValueCalculation SMOKE_BOMB = ValueCalcBuilder.of("lose_aggro")
        .baseValue(20, 40)
        .build();

}
