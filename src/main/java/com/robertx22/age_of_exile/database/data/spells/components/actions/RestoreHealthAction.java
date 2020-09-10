package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class RestoreHealthAction extends SpellAction implements ICTextTooltip {

    public RestoreHealthAction() {
        super(Arrays.asList(VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        ValueCalculationData calc = data.get(VALUE_CALCULATION);

        text.append("Restores ")
            .append(calc.getShortTooltip(info.unitdata))
            .append(" Health");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            ValueCalculationData calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.caster);

            ResourcesData.Context hctx = new ResourcesData.Context(Load.Unit(ctx.caster), ctx.caster, ResourcesData.Type.HEALTH,
                value, ResourcesData.Use.RESTORE,
                ctx.calculatedSpellData.getSpell()
            );

            targets.forEach(t -> {
                SpellHealEffect dmg = new SpellHealEffect(hctx);
                dmg.Activate();
            });
        }

    }

    public MapHolder create(ValueCalculationData calc) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        return dmg;
    }

    @Override
    public String GUID() {
        return "restore_health";
    }
}
