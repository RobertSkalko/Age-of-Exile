package com.robertx22.age_of_exile.database.data.spells.components.effects;

import com.robertx22.age_of_exile.database.data.spells.contexts.BaseSpellCtx;
import com.robertx22.age_of_exile.database.data.spells.contexts.DefaultSpellCtx;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.Arrays;
import java.util.HashMap;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageComponent extends SpellEffect {

    public DamageComponent() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public void tryActivate(BaseSpellCtx ctx, HashMap<String, Object> map) {
        if (ctx instanceof DefaultSpellCtx) {
            DefaultSpellCtx dx = (DefaultSpellCtx) ctx;

            Elements ele = ELEMENT.get(map);
            ValueCalculationData calc = VALUE_CALCULATION.get(map);

            int value = calc.getCalculatedValue(dx.caster);

            dx.targets.forEach(t -> {
                SpellDamageEffect dmg = new SpellDamageEffect(dx.caster, t, value, dx.calculatedSpellData.getSpell());
                dmg.element = ele;
                dmg.Activate();
            });

        }
    }

    @Override
    public String GUID() {
        return "damage";
    }
}
