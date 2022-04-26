package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.value_calc.DamageCalculation;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalcBuilder;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class SpellCalcs {

    public static ValueCalculation ICE_NEEDLES = ValueCalcBuilder.of(SpellKeys.ICE_NEEDLES, "Ice Needles")
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(1)
            .scaling(0.2F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(1)
            .scaling(0.2F)
            .build())
        .addAllElementsScaling(0.1F)
        .build();

    public static ValueCalculation HUNTER_POTION_HEAL = ValueCalcBuilder.of("hunter_pot_heal", "Survival Potion")
        .baseValue(100)
        .build();

    public static ValueCalculation POISON_ARROW = ValueCalcBuilder.of(SpellKeys.POISON_ARROWS, "Poison Arrow")
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
    public static ValueCalculation ENDER_ARROW = ValueCalcBuilder.of(SpellKeys.ENDER_ARROW, "Ender Arrow")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(8)
            .scaling(2)
            .build())
        .addAllElementsScaling(2)
        .build();

    public static ValueCalculation FROST_ARROW = ValueCalcBuilder.of(SpellKeys.FROST_ARROW, "Frost Arrow")
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(8)
            .scaling(2)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(8)
            .scaling(2)
            .build())
        .addAllElementsScaling(1F)
        .build();

    public static ValueCalculation PURIFYING_TOUCH = ValueCalcBuilder.of("purifying_touch")
        .baseValue(6)
        .build();

    public static ValueCalculation BOULDER_TOSS = ValueCalcBuilder.of("boulder_toss", "Boulder Toss")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1F)
        .build();

    public static ValueCalculation EXPLOSIVE_NOTE = ValueCalcBuilder.of("explosive_note", "Explosive Note")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(2.5F)
            .build())
        .addAllElementsScaling(2F)
        .build();

    public static ValueCalculation CURSE = ValueCalcBuilder.of("curse", "Curse")
        .addAllElementsScaling(0.5F)
        .build();

    public static ValueCalculation DESPAIR = ValueCalcBuilder.of("despair", "Despair")
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(5)
            .build())
        .addAllElementsScaling(2)
        .build();

    public static ValueCalculation DAMAGE_TOTEM = ValueCalcBuilder.of("damage_totem", "Damage Totem")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(8)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(8)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1)
        .build();

    public static ValueCalculation MAGMA_FLOWER_HEAL = ValueCalcBuilder.of("magma_flower_heal", "Magma Flower Heal")
        .baseValue(3)
        .addAllElementsScaling(0.25F)
        .build();

    public static ValueCalculation SEEKER_FLAMES = ValueCalcBuilder.of(SpellKeys.SEEKER_FLAMES, "Seeker Flames")
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(5)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1.25F)
        .build();
    public static ValueCalculation FROST_TRAP = ValueCalcBuilder.of(SpellKeys.FROST_TRAP, "Frost Trap")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(5)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1.25F)
        .build();
    public static ValueCalculation FIRE_TRAP = ValueCalcBuilder.of(SpellKeys.FIRE_TRAP, "Fire Trap")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Fire)
            .base(5)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1.25F)
        .build();
    public static ValueCalculation POISON_TRAP = ValueCalcBuilder.of(SpellKeys.POISON_TRAP, "Poison Trap")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(5)
            .scaling(1.5F)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Earth)
            .base(5)
            .scaling(1.5F)
            .build())
        .addAllElementsScaling(1.25F)
        .build();

    public static ValueCalculation HEALING_ARIA = ValueCalcBuilder.of("healing_aria", "Healing Aria")
        .baseValue(80)
        .build();

    public static ValueCalculation ICE_SNAKE = ValueCalcBuilder.of("ice_snake", "Ice Snake")
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

    public static ValueCalculation FROST_STEPS = ValueCalcBuilder.of("frost_steps", "Frost Steps")
        .damage(DamageCalculation.Builder.of(Elements.Water)
            .base(4)
            .scaling(1)
            .build())
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(1)
            .scaling(0.75F)
            .build())
        .addAllElementsScaling(0.5F)
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

    public static ValueCalculation POWER_CHORD = ValueCalcBuilder.of("power_chord", " Power Chord")
        .damage(DamageCalculation.Builder.of(Elements.Physical)
            .base(2)
            .scaling(1)
            .build())
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

    public static ValueCalculation ARROW_STORM = ValueCalcBuilder.of("arrow_storm")
        .attackScaling(0.6F)
        .build();

    public static ValueCalculation NATURE_BALM = ValueCalcBuilder.of("nature_balm", "Nature's Balm")
        .baseValue(5)
        .build();

    public static ValueCalculation SHOOTING_STAR = ValueCalcBuilder.of("shooting_star")
        .build();

    public static ValueCalculation HEART_OF_ICE = ValueCalcBuilder.of("heart_of_ice")
        .baseValue(50)
        .build();

    public static ValueCalculation CHILL_ERUPTION = ValueCalcBuilder.of("chill_eruption")
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
    public static ValueCalculation TOTEM_HEAL = ValueCalcBuilder.of("totem_heal", "Totem Heal")
        .baseValue(3)
        .build();
    public static ValueCalculation TOTEM_MANA = ValueCalcBuilder.of("totem_mana", "Totem Mana")
        .baseValue(6)
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
