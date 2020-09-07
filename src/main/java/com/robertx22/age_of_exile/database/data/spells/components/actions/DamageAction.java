package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageAction extends SpellAction {

    public DamageAction() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, HashMap<String, Object> map) {

        Elements ele = ELEMENT.get(map);
        ValueCalculationData calc = VALUE_CALCULATION.get(map);

        int value = calc.getCalculatedValue(ctx.caster);

        targets.forEach(t -> {
            SpellDamageEffect dmg = new SpellDamageEffect(ctx.caster, t, value, ctx.calculatedSpellData.getSpell());
            dmg.element = ele;
            dmg.Activate();
        });

    }

    @Override
    public String GUID() {
        return "damage";
    }
}
