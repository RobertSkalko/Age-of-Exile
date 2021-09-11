package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.effectdatas.GiveShieldEvent;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.SECONDS;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class GiveShieldAction extends SpellAction {

    public GiveShieldAction() {
        super(Arrays.asList(VALUE_CALCULATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            ValueCalculation calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.levelProvider);

            int seconds = data.get(SECONDS)
                .intValue();

            targets.forEach(x -> {
                GiveShieldEvent effect = new GiveShieldEvent(value, seconds, ctx.caster, x);
                effect.Activate();
            });

        }

    }

    public MapHolder create(ValueCalculation calc, Double seconds) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(SECONDS, seconds);
        dmg.put(VALUE_CALCULATION, calc);
        return dmg;
    }

    @Override
    public String GUID() {
        return "give_absorb";
    }
}
