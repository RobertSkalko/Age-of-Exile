package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageAction extends SpellAction {

    public DamageAction() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Elements ele = data.getElement();
        ValueCalculationData calc = data.get(VALUE_CALCULATION);

        int value = calc.getCalculatedValue(ctx.caster);

        targets.forEach(t -> {
            SpellDamageEffect dmg = new SpellDamageEffect(ctx.caster, t, value, ctx.calculatedSpellData.getSpell());
            dmg.element = ele;
            dmg.Activate();
        });

    }

    public MapHolder create(ValueCalculationData calc, Elements ele) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        dmg.put(ELEMENT, ele.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "damage";
    }
}
