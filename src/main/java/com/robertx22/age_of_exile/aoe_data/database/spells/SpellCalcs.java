package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.value_calc.DamageCalculation;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalcBuilder;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class SpellCalcs {

    public static ValueCalculation HEALING_ARIA = ValueCalcBuilder.of("healing_aria", "Healing Aria")
        .baseValue(80)
        .build();

    public static ValueCalculation EXPLOSIVE_ARROW = ValueCalcBuilder.of("explosive_arrow", "Explosive Arrow")
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(5)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .build())
        .addAllElementsScaling(2)
        .build();

    public static ValueCalculation POISON_CLOUD = ValueCalcBuilder.of("poison_cloud", "Poison Cloud")
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(2)
            .scaling(0.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(2)
            .scaling(0.5F)
            .build())
        .addAllElementsScaling(0.3F)
        .build();

    public static ValueCalculation METEOR = ValueCalcBuilder.of("meteor", "Meteor")
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

    public static ValueCalculation METEOR_STRIKE = ValueCalcBuilder.of("meteor_strike", "Meteor Strike")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(3)
            .scaling(1.25F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(3)
            .scaling(1.25F)
            .build())
        .addAllElementsScaling(1.5F)
        .build();

    public static ValueCalculation FROST_NOVA = ValueCalcBuilder.of("frost_nova", "Frost Nova")
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

    public static ValueCalculation TIDAL_WAVE = ValueCalcBuilder.of("tidal_wave", "Tidal Wave")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(3)
            .scaling(1.25F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(3)
            .scaling(1.25F)
            .build())
        .addAllElementsScaling(1)
        .build();

    public static ValueCalculation VENOM_STRIKE = ValueCalcBuilder.of("venom_strike", "Venom Strike")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(2)
            .scaling(1F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(2)
            .scaling(1F)
            .build())
        .addAllElementsScaling(0.75F)
        .build();

    public static ValueCalculation MAGIC_PROJECTILE = ValueCalcBuilder.of("magic_projectile", "Magic Projectile")
        .addAllElementsScaling(0.8F)
        .build();

    public static ValueCalculation POWER_CHORD = ValueCalcBuilder.of("power_chord")
        .addAllElementsScaling(0.75F)
        .build();

    public static ValueCalculation POISON = ValueCalcBuilder.of("poison", "Poison Tick")
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(2)
            .build())
        .build();

    // todo redo all these
    public static ValueCalculation BLEED = ValueCalcBuilder.of("bleed")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(4)
            .build())
        .build();

    public static ValueCalculation BURN = ValueCalcBuilder.of("burn")
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(4)
            .build())
        .build();

    public static ValueCalculation PETRIFY = ValueCalcBuilder.of("petrify")
        .baseValue(10)
        .build();

    public static ValueCalculation TORMENT = ValueCalcBuilder.of("torment")
        .baseValue(6)
        .build();
    public static ValueCalculation DESPAIR = ValueCalcBuilder.of("despair")
        .baseValue(6)
        .build();

    public static ValueCalculation WHIRLWIND = ValueCalcBuilder.of("whirlwind")
        .attackScaling(0.6F)
        .build();
    public static ValueCalculation BREATH = ValueCalcBuilder.of("breath")
        .build();

    public static ValueCalculation ARROW_STORM = ValueCalcBuilder.of("arrow_storm")
        .attackScaling(0.6F)
        .build();

    public static ValueCalculation RANGER_TRAP = ValueCalcBuilder.of("ranger_trap")
        .attackScaling(1F)
        .build();
    public static ValueCalculation AWAKEN_MANA = ValueCalcBuilder.of("awaken_mana")
        .baseValue(100)
        .build();
    public static ValueCalculation HUNTER_POTION_HEAL = ValueCalcBuilder.of("hunter_pot_heal")
        .baseValue(100)
        .build();

    public static ValueCalculation NATURE_BALM = ValueCalcBuilder.of("nature_balm")
        .baseValue(5)
        .build();

    public static ValueCalculation SHOOTING_STAR = ValueCalcBuilder.of("shooting_star")
        .build();

    public static ValueCalculation HEALING_AURA = ValueCalcBuilder.of("healing_aura")
        .baseValue(6)
        .build();
    public static ValueCalculation HEART_OF_ICE = ValueCalcBuilder.of("heart_of_ice")
        .baseValue(50)
        .build();

    public static ValueCalculation CHILL_ERUPTION = ValueCalcBuilder.of("chill_eruption")
        .build();

    public static ValueCalculation SHOUT_WARN = ValueCalcBuilder.of("shout_warn")
        .statScaling(Health.getInstance(), 0.1F)
        .build();
    public static ValueCalculation CHARGED_BOLT = ValueCalcBuilder.of("charged_bolt")
        .attackScaling(1F)
        .build();
    public static ValueCalculation CHARGE = ValueCalcBuilder.of("charge")
        .attackScaling(0.6F)
        .build();
    public static ValueCalculation TAUNT = ValueCalcBuilder.of("taunt")
        .statScaling(Health.getInstance(), 0.1F)
        .build();
    public static ValueCalculation PULL = ValueCalcBuilder.of("pull")
        .attackScaling(0.3F)
        .build();
    public static ValueCalculation SHRED = ValueCalcBuilder.of("shred")
        .attackScaling(0.6F)
        .build();
    public static ValueCalculation TOTEM_HEAL = ValueCalcBuilder.of("totem_heal")
        .baseValue(3)
        .build();
    public static ValueCalculation TOTEM_MANA = ValueCalcBuilder.of("totem_mana")
        .baseValue(6)
        .build();
    public static ValueCalculation CURSE = ValueCalcBuilder.of("curse")
        .build();
    public static ValueCalculation BLACK_HOLE = ValueCalcBuilder.of("black_hole")
        .build();
    public static ValueCalculation CHILLING_FIELD = ValueCalcBuilder.of("chilling_field")
        .build();
    public static ValueCalculation SMOKE_BOMB = ValueCalcBuilder.of("lose_aggro")
        .baseValue(40)
        .build();

    public static void init() {

    }

}
