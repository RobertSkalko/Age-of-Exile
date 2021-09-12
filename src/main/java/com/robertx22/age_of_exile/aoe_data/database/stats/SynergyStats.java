package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.NatureSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.Arrays;

public class SynergyStats {

    public static void loadClass() {

    }

    public static DataPackStatAccessor<SpellAndEffect> SPELl_CHANCE_TO_APPLY_ELEMENTAL_EFFECT = DatapackStatBuilder
        .<SpellAndEffect>of(x -> x.spell + "_chance_of_" + x.effect.effectId, x -> x.effect.element)
        .addAllOfType(Arrays.asList(
                new SpellAndEffect(WaterSpells.FROSTBALL_ID, NegativeEffects.CHILL),
                new SpellAndEffect(FireSpells.FIREBALL_ID, NegativeEffects.BURN),
                new SpellAndEffect(NatureSpells.POISONBALL_ID, NegativeEffects.POISON)
            )
        )
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(x -> StatConditions.IS_SPECIFIC_SPELL.get(x.spell))
        .addEffect(x -> StatEffects.GIVE_EFFECT_TO_TARGET.get(x.effect))
        .setLocName(x -> Stat.format(
            "Adds " + Stat.VAL1 + "% chance of applying " + x.effect.locname
        ))
        .setLocDesc(x -> "Chance to give effect")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.max = 100;
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();
}
