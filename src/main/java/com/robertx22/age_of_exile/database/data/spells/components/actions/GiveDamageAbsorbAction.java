package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageAbsorbEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.SECONDS;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class GiveDamageAbsorbAction extends SpellAction implements ICTextTooltip {

    public GiveDamageAbsorbAction() {
        super(Arrays.asList(VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data, CalculatedSpellData spelldata) {
        MutableText text = new LiteralText("");

        ValueCalculation calc = data.get(VALUE_CALCULATION);

        int seconds = data.get(SECONDS)
            .intValue();

        text.append("Gives ")
            .append(calc.getShortTooltip(spelldata.level))
            .append(" Damage Absorption for " + seconds + "s");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            ValueCalculation calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.caster, ctx.calculatedSpellData);

            int seconds = data.get(SECONDS)
                .intValue();

            targets.forEach(x -> {
                DamageAbsorbEffect effect = new DamageAbsorbEffect(value, seconds, ctx.caster, x);
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
