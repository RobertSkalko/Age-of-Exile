package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class RestoreManaAction extends SpellAction implements ICTextTooltip {

    public RestoreManaAction() {
        super(Arrays.asList(VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        ValueCalculationData calc = data.get(VALUE_CALCULATION);

        text.append("Restores ")
            .append(calc.getShortTooltip(info.unitdata))
            .append(" Mana");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            ValueCalculationData calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.caster);
            value *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.HEALING);

            ResourcesData.Context hctx = new ResourcesData.Context(Load.Unit(ctx.caster), ctx.caster, ResourcesData.Type.MANA,
                value, ResourcesData.Use.RESTORE,
                ctx.calculatedSpellData.getSpell()
            );

            targets.forEach(x -> {
                Load.Unit(x)
                    .modifyResource(hctx);
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
        return "restore_mana";
    }
}
