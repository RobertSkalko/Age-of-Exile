package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs;

import com.robertx22.mine_and_slash.uncommon.localization.Words;

public enum SC {

    MANA_COST(0, Words.Mana_Cost),
    RADIUS(0, Words.Radius),
    PROJECTILE_COUNT(1, Words.ProjectileCount),
    CAST_TIME_TICKS(0, Words.CastTimeTicks),
    COOLDOWN_SECONDS(0, Words.CooldownSeconds),
    COOLDOWN_TICKS(0, Words.CooldownTicks),
    SHOOT_SPEED(0.05F, Words.ShootSpeed),
    SUMMONED_ENTITIES(1, Words.SummonedEntities),
    CHANCE(0, Words.Chance),
    CDR_EFFICIENCY(0, Words.CooldownReductionEfficiency),
    DURATION_TICKS(0, Words.DurationTicks),
    TICK_RATE(-100, Words.TickRate),
    TIMES_TO_CAST(1, Words.TimesToCast),
    BASE_VALUE(0, null),
    AMOUNT(0, Words.Amount),
    ATTACK_SCALE_VALUE(0, null);

    public float min;

    SC(float min, Words word) {
        this.word = word;
        this.min = min;
    }

    public Words word;

    public final boolean shouldAddToTooltip() {
        return word != null;
    }
}
